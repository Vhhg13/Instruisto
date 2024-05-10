package com.example.instruisto.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.model.Exercise
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.model.Lesson
import com.example.instruisto.util.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

@HiltViewModel(assistedFactory = LessonViewModel.Factory::class)
class LessonViewModel @AssistedInject constructor(@Assisted private val lessonId: Int, private val lessons: LessonRepository): ViewModel() {
    @AssistedFactory interface Factory{ fun create(id: Int): LessonViewModel }

    private val _currentProgress = MutableStateFlow(0)
    val currentProgress = _currentProgress.asStateFlow()

    private val _step = MutableStateFlow<LessonState>(LessonState.StartLessonState)
    val step = _step.asStateFlow()

    private lateinit var progressTracker: ProgressTracker

    private lateinit var steps: Queue<Lesson.Step>

    init {
        viewModelScope.launch {
            val lesson = lessons.byId(lessonId)
            if(lesson is Result.Success){
                steps = LinkedList<Lesson.Step>().apply {
                    addAll(lesson.value.steps)
                }
                val firstStep = steps.poll()
                progressTracker = ProgressTracker(lesson.value.steps.size)
                _step.value = when(firstStep){
                    is Exercise ->
                        LessonState.ExerciseLessonState(firstStep)
                    is GrammarPoint ->
                        LessonState.GrammarLessonState(firstStep)
                    null ->
                        LessonState.EndLessonState(Pair(0, 0))
                }

            }else{
                _step.value = LessonState.ErrorLessonState
            }
        }
    }

    var usersAnswer: String = ""

    fun next(){
        when(val currentStep = step.value){
            is LessonState.EndLessonState -> {}
            is LessonState.ExerciseLessonState -> onExerciseLessonState(currentStep)
            is LessonState.GrammarLessonState -> {
                progressTracker.check()
                nextStep()
            }
            LessonState.StartLessonState -> {}
            LessonState.CorrectLessonState -> nextStep()
            is LessonState.IncorrectLessonState -> nextStep()
            LessonState.ErrorLessonState -> {}
        }
    }

    private fun nextStep() {
        _step.value = when(val nextStep = steps.poll()){
            is Exercise -> LessonState.ExerciseLessonState(nextStep)
            is GrammarPoint -> LessonState.GrammarLessonState(nextStep)
            null -> LessonState.EndLessonState(progressTracker.performance)
        }
    }

    private fun onExerciseLessonState(e: LessonState.ExerciseLessonState) {
        val isCorrect = e.exercise.answerText.trim().lowercase() == usersAnswer.trim().lowercase()
        progressTracker.check(isCorrect)
        if(isCorrect){
            _step.value = LessonState.CorrectLessonState
        }else{
            _step.value = LessonState.IncorrectLessonState(e.exercise.answerText)
            steps.offer(e.exercise)
        }
    }

    private inner class ProgressTracker(private val size: Int){
        private var correct = 0
        private var incorrect = 0

        val percentage: Int get() = correct*100/size
        val performance: Pair<Int, Int> get() = Pair(incorrect, (correct-incorrect)*100/size)

        fun check(isCorrect: Boolean = true): Int {
            if (isCorrect) ++correct else ++incorrect
            _currentProgress.value = percentage
            return percentage
        }
    }
}



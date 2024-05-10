package com.example.instruisto.ui.lesson

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentExerciseBinding
import com.example.instruisto.model.Exercise
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseFragment : Fragment() {
    private lateinit var binding: FragmentExerciseBinding
    private val viewModel by activityViewModels<LessonViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<LessonViewModel.Factory> {
                it.create(requireActivity().intent.extras?.getInt(LessonActivity.LESSON_EXTRA) ?: -42)
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        val playSoundListener = { view: View ->
            MediaPlayer.create(requireContext(), R.raw.lingvo_internacia).start()
        }
        binding.playButton.setOnClickListener(playSoundListener)
        binding.substituteTextview.setOnClickListener(playSoundListener)
        binding.translateTextview.setOnClickListener(playSoundListener)
        observe(viewModel.step){
            when(it){
                LessonState.CorrectLessonState -> renderCorrectLessonState()
                is LessonState.ExerciseLessonState -> renderExerciseLessonState(it.exercise)
                is LessonState.IncorrectLessonState -> renderIncorrectLessonState(it.correctAnswer)
                else -> {}
            }
        }
        binding.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.usersAnswer = text.toString()
        }
        return binding.root
    }

    private fun renderIncorrectLessonState(correctAnswer: String) {
        binding.msgWrong.visibility = View.VISIBLE
        binding.correctAnswer.text = correctAnswer
        binding.correctAnswer.visibility = View.VISIBLE
    }

    private fun renderExerciseLessonState(e: Exercise){
        binding.editText.setText("")
        binding.msgCorrect.visibility = View.GONE
        binding.msgWrong.visibility = View.GONE
        binding.correctAnswer.visibility = View.GONE
        when(e.type){
            Exercise.Type.TRANSLATION -> {
                binding.translateTextview.visibility = View.VISIBLE
                binding.substituteTextview.visibility = View.GONE
                binding.playButton.visibility = View.GONE
                binding.translateTextview.text = e.questionText
            }
            Exercise.Type.SUBSTITUTION -> {
                binding.translateTextview.visibility = View.GONE
                binding.substituteTextview.visibility = View.VISIBLE
                binding.playButton.visibility = View.GONE
                binding.substituteTextview.text = e.questionText
            }
            Exercise.Type.LISTENING -> {
                binding.translateTextview.visibility = View.GONE
                binding.substituteTextview.visibility = View.GONE
                binding.playButton.visibility = View.VISIBLE
            }
        }
    }

    private fun renderCorrectLessonState() {
        binding.msgCorrect.visibility = View.VISIBLE
    }

    private fun <T> observe(flow: StateFlow<T>, block: (T) -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collect(block)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("usersAnswer", viewModel.usersAnswer)
    }
}
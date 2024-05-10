package com.example.instruisto.ui.lesson

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.instruisto.R
import com.example.instruisto.databinding.ActivityLessonBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LessonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    companion object{
        const val LESSON_EXTRA = "tk.vhhg.instruisto.lessonId"
    }
    private val viewModel: LessonViewModel by viewModels<LessonViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<LessonViewModel.Factory> {
                it.create(intent.extras!!.getInt(LESSON_EXTRA))
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findViewById<FragmentContainerView>(R.id.fragment_container_view).getFragment<NavHostFragment>().navController
        binding.next.setOnClickListener { viewModel.next() }
        observe(viewModel.step){
            when(it){
                is LessonState.EndLessonState ->{
                    binding.next.visibility = View.GONE
                    binding.grammarButton.visibility = View.GONE
                    navController.navigate(R.id.toEnd)
                }
                is LessonState.ExerciseLessonState ->
                    if(navController.currentDestination?.id != R.id.exerciseFragment)
                        navController.navigate(R.id.toExercise)
                is LessonState.GrammarLessonState ->
                    if(navController.currentDestination?.id != R.id.inLessonGrammarFragment)
                        navController.navigate(R.id.toGrammar)
                LessonState.ErrorLessonState -> TODO()
                else -> {}
            }
        }
        observe(viewModel.currentProgress){
            binding.progressBar.progress = it
        }
    }

    private fun <T> observe(flow: StateFlow<T>, block: (T) -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collect(block)
            }
        }
    }
}
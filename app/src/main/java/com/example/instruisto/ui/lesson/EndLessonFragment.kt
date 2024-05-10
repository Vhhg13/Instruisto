package com.example.instruisto.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.instruisto.databinding.FragmentEndLessonBinding
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EndLessonFragment : Fragment() {
    private lateinit var binding: FragmentEndLessonBinding
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
        binding = FragmentEndLessonBinding.inflate(inflater, container, false)
        binding.goBack.setOnClickListener { requireActivity().finish() }
        observe(viewModel.step){
            if(it is LessonState.EndLessonState){
                binding.performance.text = it.performance.second.toString()
                binding.numberOfMistakes.text = "${it.performance.first}%"
            }
        }
        return binding.root
    }

    private fun <T> observe(flow: StateFlow<T>, block: (T) -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collect(block)
            }
        }
    }
}
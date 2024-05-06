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
import com.example.instruisto.databinding.FragmentInLessonGrammarBinding
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

class InLessonGrammarFragment : Fragment() {
    private lateinit var binding: FragmentInLessonGrammarBinding
    private val viewModel by activityViewModels<LessonViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<LessonViewModel.Factory> {
                it.create(requireActivity().intent.extras?.getInt(LessonActivity.LESSON_EXTRA) ?: -42)
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInLessonGrammarBinding.inflate(inflater, container, false)
        return binding.root
    }
}
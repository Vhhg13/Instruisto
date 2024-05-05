package com.example.instruisto.ui.lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentInLessonGrammarBinding

class InLessonGrammarFragment : Fragment() {
    private lateinit var binding: FragmentInLessonGrammarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInLessonGrammarBinding.inflate(inflater, container, false)
        return binding.root
    }
}
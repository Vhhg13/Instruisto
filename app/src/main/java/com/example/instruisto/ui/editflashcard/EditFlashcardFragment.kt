package com.example.instruisto.ui.editflashcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentEditFlashcardBinding

class EditFlashcardFragment : Fragment() {
    lateinit var binding: FragmentEditFlashcardBinding
    val args: EditFlashcardFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditFlashcardBinding.inflate(inflater, container, false)
        binding.frontEt.setText(args.flashcard?.front)
        binding.backEt.setText(args.flashcard?.back)
        return binding.root
    }
}
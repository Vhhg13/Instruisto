package com.example.instruisto.ui.reviewflashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentReviewFlashcardsBinding

class ReviewFlashcardsFragment : Fragment() {
    private val args: ReviewFlashcardsFragmentArgs by navArgs()
    private lateinit var binding: FragmentReviewFlashcardsBinding
    private lateinit var shown: List<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReviewFlashcardsBinding.inflate(inflater, container, false)
        val deckId = args.deckId
        shown = listOf(
            binding.backText,
            binding.dontRemember,
            binding.remember,
            binding.image
        )
        binding.turnOver.setOnClickListener{ showContents() }
        binding.remember.setOnClickListener {
            hideContents()
        }
        binding.dontRemember.setOnClickListener {
            hideContents()
        }
        return binding.root
    }

    private fun showContents(){
        binding.turnOver.visibility = View.GONE
        shown.forEach { view ->
            view.visibility = View.VISIBLE
        }
    }

    private fun hideContents(){
        binding.turnOver.visibility = View.VISIBLE
        shown.forEach { view ->
            view.visibility = View.GONE
        }
    }
}
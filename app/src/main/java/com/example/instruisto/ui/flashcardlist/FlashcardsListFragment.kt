package com.example.instruisto.ui.flashcardlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.instruisto.databinding.FragmentFlashcardListBinding
import com.example.instruisto.model.Flashcard
import java.util.Date

class FlashcardsListFragment : Fragment() {
    val args: FlashcardsListFragmentArgs by navArgs()
    private lateinit var binding: FragmentFlashcardListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFlashcardListBinding.inflate(layoutInflater, container, false)
        //requireActivity().actionBar?.title = "Deck name"
        val navController = findNavController()
        val adapter = FlashcardListAdapter{
            val action = FlashcardsListFragmentDirections.actionDestFlashcardListToEditFlashcardFragment(it, it.deck)
            navController.navigate(action)
        }
        binding.recycler.adapter = adapter
        adapter.submitList((0..20).map {
            Flashcard(
                id = it,
                "a", "b",
                Date(),
                "",
                1
            )
        })
        binding.startFlashcardsButton.setOnClickListener {
            val action = FlashcardsListFragmentDirections.actionDestFlashcardListToReviewFlashcardsFragment(-1)
            navController.navigate(action)
        }
        return binding.root
    }
}
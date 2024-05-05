package com.example.instruisto.ui.deckslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.instruisto.databinding.FragmentDecksListBinding
import com.example.instruisto.model.Deck

class DecksListFragment : Fragment() {
    private lateinit var binding: FragmentDecksListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDecksListBinding.inflate(layoutInflater, container, false)
        val navController = findNavController()
        binding.recycler.adapter = DecksListAdapter(('a'.code .. 'l'.code).map {
            Deck(
                id = it,
                name = it.toChar().toString(),
                plan = "$it/$it/$it",
                listOf()
            )
        }){
            val action = DecksListFragmentDirections.actionDestDecksToFlashcardList(it, it.name)
            navController.navigate(action)
        }
        binding.startFlashcardsButton.setOnClickListener {
            val action = DecksListFragmentDirections.actionDestDecksToReviewFlashcardsFragment(-1)
            navController.navigate(action)
        }
        return binding.root
    }
}
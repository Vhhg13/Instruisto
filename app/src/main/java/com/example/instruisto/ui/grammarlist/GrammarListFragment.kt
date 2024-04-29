package com.example.instruisto.ui.grammarlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instruisto.databinding.FragmentGrammarListBinding
import com.example.instruisto.model.Lesson
import com.example.instruisto.util.GrammarListAdapter

class GrammarListFragment : Fragment() {
    private lateinit var binding: FragmentGrammarListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGrammarListBinding.inflate(layoutInflater, container, false)
        val recycler = binding.recycler
        val list: List<Lesson.GrammarPoint> = (1L..20).map {
            Lesson.GrammarPoint(id = it, name = "Grammar$it", description = "desc$it")
        }
        val navController = findNavController()
        val adapter = GrammarListAdapter{ id ->
            val action = GrammarListFragmentDirections.actionDestGrammarToDestGrammarPoint(list.find { it.id == id }!!)
            navController.navigate(action)
        }
        recycler.adapter = adapter
        adapter.submitList(list)
        return binding.root
    }
}
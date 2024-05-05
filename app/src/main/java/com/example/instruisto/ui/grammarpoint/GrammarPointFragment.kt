package com.example.instruisto.ui.grammarpoint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.instruisto.databinding.FragmentGrammarPointBinding
import com.example.instruisto.model.GrammarPoint

class GrammarPointFragment : Fragment() {
    val args: GrammarPointFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentGrammarPointBinding.inflate(inflater)
        val point: GrammarPoint = args.grammarPoint
        binding.title.text = point.name
        binding.description.text = point.description
        return binding.root
    }
}
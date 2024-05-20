package com.example.instruisto.ui.grammarlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.instruisto.databinding.FragmentGrammarListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GrammarListFragment : Fragment() {
    private lateinit var binding: FragmentGrammarListBinding
    private val viewModel: GrammarListViewModel by viewModels()
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
        val navController = findNavController()
        val adapter = GrammarListAdapter{ point ->
            val action = GrammarListFragmentDirections.actionDestGrammarToDestGrammarPoint(point)
            navController.navigate(action)
        }
        recycler.adapter = adapter
        binding.til.setEndIconOnClickListener {
            binding.et.text?.clear()
            requireActivity().getSystemService(InputMethodManager::class.java)
                .hideSoftInputFromWindow(
                    binding.root.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
        }
        binding.et.doOnTextChanged { text, _, _, _ ->
            viewModel.filter(binding.inContentsChip.isChecked, binding.inNameChip.isChecked, text)
        }
        binding.inNameChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.filter(binding.inContentsChip.isChecked, isChecked, binding.et.text)
        }
        binding.inContentsChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.filter(isChecked, binding.inNameChip.isChecked, binding.et.text)
        }
        observe(viewModel.uiState){
            adapter.submitList(it)
            if(it.isEmpty())
                binding.placeholder.visibility = View.VISIBLE
            else if(binding.placeholder.visibility != View.GONE)
                binding.placeholder.visibility = View.GONE
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
package com.example.instruisto.ui.grammarlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.model.GrammarPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrammarListViewModel @Inject constructor(private val grammarRepo: GrammarRepository): ViewModel() {

    private val _uiState = MutableStateFlow(listOf<GrammarPoint>())
    val uiState = _uiState.asStateFlow()
    private lateinit var originalList: List<GrammarPoint>
    init {
        viewModelScope.launch {
            originalList = grammarRepo.all()
            _uiState.value = originalList
        }
    }
    fun filter(inContents: Boolean, inName: Boolean, text: CharSequence?) {
        _uiState.value =
            if(text == null)
                originalList
            else
                originalList.filter {
                    (if(inName) it.name.contains(text, ignoreCase = true) else false) ||
                            (if(inContents) it.description.contains(text, ignoreCase = true) else false)
                }
    }
}
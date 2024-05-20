package com.example.instruisto.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instruisto.App
import com.example.instruisto.data.repo.AuthRepository
import com.example.instruisto.data.repo.LessonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: AuthRepository,
    @ApplicationContext app: Context,
    private val lessons: LessonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val state = ProfileState(
                (app as App).username ?: "error",
                lessons.progress()
            )
            _uiState.value = state
        }
    }
}
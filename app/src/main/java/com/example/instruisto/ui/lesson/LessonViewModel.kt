package com.example.instruisto.ui.lesson

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel(assistedFactory = LessonViewModel.Factory::class)
class LessonViewModel @AssistedInject constructor(@Assisted val lessonId: Int): ViewModel() {
    @AssistedFactory interface Factory{ fun create(id: Int): LessonViewModel }
    private val _uiState = MutableStateFlow(LessonState())
    val uiState = _uiState.asStateFlow()
}
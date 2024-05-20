package com.example.instruisto.ui.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instruisto.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(private val authRepo: AuthRepository): ViewModel() {
    enum class AuthStatus{
        USERNAME_TAKEN,
        UNMATCHING_PASSWORDS,
        SUCCESS,
        EMPTY
    }

    private val _uiState = MutableStateFlow(AuthStatus.EMPTY)
    val uiState = _uiState.asStateFlow()

    fun register(username: String, pwd1: String, pwd2: String){
        if(pwd1 != pwd2){
            _uiState.value = AuthStatus.UNMATCHING_PASSWORDS
            return
        }
        viewModelScope.launch {
            _uiState.value =
                if(!authRepo.register(username, pwd1))
                    AuthStatus.USERNAME_TAKEN
                else
                    AuthStatus.SUCCESS
        }
    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            _uiState.value =
                if(authRepo.login(username, password))
                    AuthStatus.SUCCESS
                else
                    AuthStatus.USERNAME_TAKEN
        }
    }

    fun clearCredentials() { _uiState.value = AuthStatus.EMPTY }
}
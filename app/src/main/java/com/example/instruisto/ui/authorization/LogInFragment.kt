package com.example.instruisto.ui.authorization

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentLogInBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    val viewModel: AuthorizationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {super.onCreate(savedInstanceState)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.gotoSignupTw.setOnClickListener {
            viewModel.clearCredentials()
            navController.navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.logInButton.setOnClickListener { viewModel.login(
            binding.loginEt.text.toString(),
            binding.password1Et.text.toString()
        ) }

        observe(viewModel.uiState){
            when(it){
                AuthorizationViewModel.AuthStatus.USERNAME_TAKEN -> {
                    binding.pwd1TIL.error = context?.getString(R.string.word_invalid_username_or_password)
                    binding.pwd1TIL.isErrorEnabled = true
                }
                AuthorizationViewModel.AuthStatus.UNMATCHING_PASSWORDS -> throw Exception("Should've been unreachable")
                AuthorizationViewModel.AuthStatus.SUCCESS -> {
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }
                AuthorizationViewModel.AuthStatus.EMPTY -> {
                    binding.loginTIL.error = ""
                    binding.loginTIL.isErrorEnabled = false
                    binding.pwd1TIL.error = ""
                    binding.pwd1TIL.isErrorEnabled = false
                    binding.pwd2TIL.error = ""
                    binding.pwd2TIL.isErrorEnabled = false
                }
            }
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
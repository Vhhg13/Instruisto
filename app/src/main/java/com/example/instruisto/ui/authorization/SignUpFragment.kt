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
import com.example.instruisto.databinding.FragmentSignUpBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    val viewModel: AuthorizationViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.signUpButton.setOnClickListener { viewModel.register(
            binding.loginEt.text.toString(),
            binding.password1Et.text.toString(),
            binding.password2Et.text.toString()
        ) }
        binding.gotoLoginTw.setOnClickListener {
            viewModel.clearCredentials()
            navController.navigate(R.id.action_signUpFragment_to_logInFragment)
        }
        observe(viewModel.uiState){
            when(it){
                AuthorizationViewModel.AuthStatus.USERNAME_TAKEN -> {
                    binding.loginTIL.error = context?.getString(R.string.word_username_taken)
                    binding.loginTIL.isErrorEnabled = true
                }
                AuthorizationViewModel.AuthStatus.UNMATCHING_PASSWORDS -> {
                    binding.pwd2TIL.error = context?.getString(R.string.word_passwords_do_not_match)
                    binding.pwd2TIL.isErrorEnabled = true
                }
                AuthorizationViewModel.AuthStatus.SUCCESS -> {
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }
                AuthorizationViewModel.AuthStatus.EMPTY -> {
                    binding.loginTIL.error = ""
                    binding.loginTIL.isErrorEnabled = false
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
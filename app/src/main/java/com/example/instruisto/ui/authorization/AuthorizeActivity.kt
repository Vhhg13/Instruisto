package com.example.instruisto.ui.authorization

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.instruisto.databinding.ActivityAuthorizeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizeActivity : AppCompatActivity() {
    val viewModel: AuthorizationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
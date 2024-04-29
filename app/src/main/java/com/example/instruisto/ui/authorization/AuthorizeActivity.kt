package com.example.instruisto.ui.authorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.navigation.fragment.NavHostFragment
import com.example.instruisto.R
import com.example.instruisto.databinding.ActivityAuthorizeBinding
import kotlin.random.Random


class AuthorizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val navController = binding.authFragmentContainerView.getFragment<NavHostFragment>().navController

    }
}
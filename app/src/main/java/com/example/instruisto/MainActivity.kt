package com.example.instruisto

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.instruisto.data.repo.AuthRepository
import com.example.instruisto.databinding.ActivityMainBinding
import com.example.instruisto.ui.authorization.AuthorizeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    @Inject lateinit var authRepo: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val app = applicationContext as App
        if(app.jwt?.isEmpty() != false){
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                setupNavigation()
            }.launch(Intent(this, AuthorizeActivity::class.java))
        }else{
            setupNavigation()
        }

    }

    private fun setupNavigation(){
        val drawerLayout = binding.drawerLayout
        val toolbar = binding.toolbar
        val navView = binding.navigationView
        val navController = binding.navHostFragmentContainer.getFragment<NavHostFragment>().navController
        navController.setGraph(R.navigation.nav_graph)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.dest_decks, R.id.dest_profile, R.id.dest_grammar, R.id.dest_lesson_list
        ), drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
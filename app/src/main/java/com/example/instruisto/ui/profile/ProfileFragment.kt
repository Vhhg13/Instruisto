package com.example.instruisto.ui.profile

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.instruisto.App
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentProfileBinding
import com.example.instruisto.ui.authorization.AuthorizeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            findNavController().navigate(R.id.action_dest_profile_to_dest_lesson_list)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val sp = context?.getSharedPreferences(App.SHARED_PREF_NAME, Context.MODE_PRIVATE)!!
        val map = hashMapOf(true to (UiModeManager.MODE_NIGHT_YES to AppCompatDelegate.MODE_NIGHT_YES), false to (UiModeManager.MODE_NIGHT_NO to AppCompatDelegate.MODE_NIGHT_NO))
        binding.darkModeToggleSwitch.isChecked = sp.getBoolean("isNight", false)
        binding.darkModeToggleSwitch.setOnClickListener {
            val isNight = sp.getBoolean("isNight", false).not()
            //Toast.makeText(context, "isNight is $isNight", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (context?.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).setApplicationNightMode(map[isNight]!!.first)
            }else{
                Log.d("TAG", "onCreateView: AppCompatDelefate")
                AppCompatDelegate.setDefaultNightMode(map[isNight]!!.second)
            }
            sp.edit {
                putBoolean("isNight", isNight)
            }
        }
        binding.signOut.setOnClickListener {
            (requireActivity().applicationContext as App).jwt = null
            launcher.launch(Intent(requireActivity(), AuthorizeActivity::class.java))
        }
        observe(viewModel.uiState){
            binding.loginTextView.text = it.username
            binding.progressValueTextView.text = getString(R.string.progress_percentage, it.progress)
            binding.lessonsProgressBar.progress = it.progress
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
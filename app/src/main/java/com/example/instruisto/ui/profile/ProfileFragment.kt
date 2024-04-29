package com.example.instruisto.ui.profile

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.instruisto.App
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        return binding.root
    }
}
package com.example.instruisto.ui

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.instruisto.App
import com.example.instruisto.databinding.FragmentComponentsBinding

class ComponentsFragment : Fragment() {
    private lateinit var binding: FragmentComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentComponentsBinding.inflate(layoutInflater, container, false)
        val map = hashMapOf(true to (UiModeManager.MODE_NIGHT_YES to AppCompatDelegate.MODE_NIGHT_YES), false to (UiModeManager.MODE_NIGHT_NO to AppCompatDelegate.MODE_NIGHT_NO))
        val sp = context?.getSharedPreferences(App.SHARED_PREF_NAME, Context.MODE_PRIVATE)!!
        binding.button.isChecked = sp.getBoolean("isNight", false)
        binding.button.setOnClickListener {
            val isNight = sp.getBoolean("isNight", false)?.not()
            //Toast.makeText(context, "isNight is $isNight", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (context?.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).setApplicationNightMode(map[isNight]!!.first)
            }else{
                AppCompatDelegate.setDefaultNightMode(map[isNight]!!.second)
            }
            sp.edit {
                putBoolean("isNight", isNight!!)
            }
        }
        binding.materialButton.setOnClickListener {

        }
        return binding.root
    }
}
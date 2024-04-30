package com.example.instruisto.ui.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.instruisto.R

class LogInFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        view.findViewById<TextView>(R.id.goto_signup_tw).setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        view.findViewById<Button>(R.id.log_in_button).setOnClickListener {
            //view.findNavController().navigate(R.id.action_dest_login_to_dest_profile)
            activity?.finish()
        }
        view.findViewById<Button>(R.id.cross_button).setOnClickListener {
            activity?.finish()
        }
        return view
    }

}
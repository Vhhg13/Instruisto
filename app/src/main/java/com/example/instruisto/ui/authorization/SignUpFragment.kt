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

class SignUpFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            activity?.finish()
        }
        view.findViewById<TextView>(R.id.goto_login_tw).setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }
        view.findViewById<Button>(R.id.cross_button).setOnClickListener {
            activity?.finish()
        }
        return view
    }

}
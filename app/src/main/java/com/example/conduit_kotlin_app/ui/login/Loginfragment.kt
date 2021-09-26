package com.example.conduit_kotlin_app.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Loginfragment : Fragment(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            Log.d("login", "email = ${binding.tfEmail.text}, password = ${binding.tfPassword.text.toString()}")
            viewModel.login(email = binding.tfEmail.text.toString() , password = binding.tfPassword.text.toString())
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginfragment_to_signUpFragment2)
        }

        viewModel.isVerified.observe(viewLifecycleOwner){ isVerified ->
            if(isVerified){
                findNavController().navigate(R.id.action_loginfragment_to_listArticlesFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.conduit_kotlin_app.ui.signUp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.conduit_kotlin_app.R
import com.example.conduit_kotlin_app.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_signup) {
    private val viewModel by viewModels<SignUpViewModel>()
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupBinding.bind(view)

        binding.btnSignup.setOnClickListener {
        Log.d("fragment signup","signup button clicked")

        //   viewModel.signUp()
        }
        binding.btnLogin.setOnClickListener {
           findNavController().popBackStack()
        }
        viewModel.isVerified.observe(viewLifecycleOwner){ isVerified ->
            if(isVerified){
                findNavController().navigate(R.id.action_signUpFragment_to_listArticlesFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
package com.example.conduit_kotlin_app.ui.splashscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.example.conduit_kotlin_app.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splashscreen) {
    private val viewModel by viewModels<SplashScreenViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isDelayCompleted.observe(viewLifecycleOwner) { delayed ->
            if (delayed) {

                viewModel.readFromDataStore.observe(viewLifecycleOwner) { isLoggedIn ->
                    Log.d("fragment",isLoggedIn.toString())
                    if (isLoggedIn) {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_listArticlesFragment)
                    } else {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginfragment)
                    }
                }
            }

        }

    }



}
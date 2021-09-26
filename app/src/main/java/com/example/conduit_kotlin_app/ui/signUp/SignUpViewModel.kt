package com.example.conduit_kotlin_app.ui.signUp

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.launch

class SignUpViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val isVerified: MutableLiveData<Boolean> = MutableLiveData(false)
    //sign-up function
    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.signUp(username, email, password)
            if (response.isSuccessful) {

                response.body()?.user?.let {
                    dataStoreRepository.saveAuthToken(it.token)
                    dataStoreRepository.saveUsername(it.username)
                }

                dataStoreRepository.saveDataStore(true)
                isVerified.value = true
                Log.d("login", "the response of api " + response.body() + " ")
            } else {
                Log.d("login", "the response of api " + response.errorBody() + " ")
            }

        }

    }
}
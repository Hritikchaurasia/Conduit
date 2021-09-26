package com.example.conduit_kotlin_app.ui.splashscreen

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel @ViewModelInject constructor(
    private val dataStoreRepository: DataStoreRepository
) :ViewModel() {

    val isDelayCompleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val readFromDataStore:LiveData<Boolean> = dataStoreRepository.readFromDataStore.asLiveData()
    init {
        viewModelScope.launch {

            delay(2000)

            isDelayCompleted.value = true
        }

    }
}
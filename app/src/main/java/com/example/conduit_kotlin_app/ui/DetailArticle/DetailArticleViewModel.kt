package com.example.conduit_kotlin_app.ui.DetailArticle

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailArticleViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    var username: String = ""
    var isdeleteArticle: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        loadData()
    }

    fun deleteArticle(slug: String) {
        viewModelScope.launch {
            isLoading.value = true
            val token = dataStoreRepository.readAuthToken()
            repository.deleteArticle(header = token, slug = slug)
            isdeleteArticle.value = true
            //isLoading.value = false
        }
    }

    //user name here of user which we have to follow
    fun followOtherUser(username: String) {
        viewModelScope.launch {
            isLoading.value = true
            val token = dataStoreRepository.readAuthToken()
            repository.followOtherUser(header = token, username = username)
            isLoading.value = false
        }

    }

    fun loadData() {
        viewModelScope.launch {
            isLoading.value = true
            username = dataStoreRepository.readUserName()
            isLoading.value = false
        }
    }

    //add article to favorite
    fun addArticleToFavorite(slug: String) {
        viewModelScope.launch {
            isLoading.value = true
            val token = dataStoreRepository.readAuthToken()
            repository.addArticleToFavorite(header = token, slug = slug)
            isLoading.value = false
        }
    }
}
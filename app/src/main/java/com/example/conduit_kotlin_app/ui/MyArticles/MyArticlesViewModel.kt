package com.example.conduit_kotlin_app.ui.MyArticles

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.launch

class MyArticlesViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val listArticleMutableList: MutableLiveData<List<Article>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)


    init {
       loadMyArticles()
    }

    fun loadMyArticles(){
        //loading recycler view data
        viewModelScope.launch {
            isLoading.value = true
            val author:String = dataStoreRepository.readUserName()
            val response = repository.getMyarticles(author)
            if (response.isSuccessful) {
                response.body()?.let {
                    listArticleMutableList.value = it.articles
                }
                isLoading.value = false
            }


        }
    }


}
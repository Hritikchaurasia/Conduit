package com.example.conduit_kotlin_app.ui.GlobalFeedArticles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.launch

class ListArticlesViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val listArticleMutableList: MutableLiveData<List<Article>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)


    //to get list of articles response
    init {
     //   Log.d("message from viewmodel ", "init start")

        loadArticles()

    }

    fun loadArticles(){
        //loading recycler view data
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getListArticles()
            if (response.isSuccessful) {
                response.body()?.let {
                    listArticleMutableList.value = it.articles
                }
                isLoading.value = false
            }


        }
    }
}
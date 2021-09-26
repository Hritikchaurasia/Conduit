package com.example.conduit_kotlin_app.ui.userfeed

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.launch


class UserfeedViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    //"Token eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTMzMDIzLCJ1c2VybmFtZSI6Ik1hZGFubW9oYW5wYW50IiwiZXhwIjoxNjE1Mzk1MTEyfQ.6xEQ05a6JH-ED2RX-InAbvhEWQVJ4JQ-3bumXhTvz4w"

    val listArticleMutableList: MutableLiveData<List<Article>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
       loadArticles()
    }
    fun loadArticles(){
        viewModelScope.launch {

            isLoading.value = true
            //getting user auth token
            val header: String = dataStoreRepository.readAuthToken()
            Log.d("userfeed", header)
            //request
            val response = repository.userfeed(
                header = header
            )

            if (response.isSuccessful) {

                response.body()?.let {
                    listArticleMutableList.value = it.articles
                    Log.d("userfeed", response.body().toString())
                }
                isLoading.value = false
            } else {
                Log.d("userfeed", "${response.errorBody()}")
            }
        }
    }

}
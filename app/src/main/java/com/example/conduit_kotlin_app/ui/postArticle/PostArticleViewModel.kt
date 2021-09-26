package com.example.conduit_kotlin_app.ui.postArticle

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conduit_kotlin_app.data.Article
import com.example.conduit_kotlin_app.repositories.DataStoreRepository
import com.example.conduit_kotlin_app.repositories.MainRepository
import kotlinx.coroutines.launch

class PostArticleViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun postArticle(article: Article){
        viewModelScope.launch {
            isLoading.value = true
            val header : String = dataStoreRepository.readAuthToken()
            val response = repository.postArticle(header = header , article = article)
            if(response.isSuccessful){
                Log.d("post article","${response.body()}")
            }else{
                Log.d("post article","error occure")
            }
            isLoading.value = false
        }
    }
}
package com.example.conduit_kotlin_app.repositories


import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.conduit_kotlin_app.BaseApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreRepository @Inject constructor(
    private var app: BaseApplication
) {


    companion object {

        const val PREFRENCE_NAME = "custom_preference"
    }

    private object PreferencesKey {
        val isLogin = preferencesKey<Boolean>("isLogin")
        val authToken = preferencesKey<String>("authToken")
        val username = preferencesKey<String>("username")
    }

    private val dataStore: DataStore<Preferences> = app.createDataStore(
        name = PREFRENCE_NAME,
    )

    suspend fun saveDataStore(islogin: Boolean) {
        Log.d("login", islogin.toString())
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isLogin] = islogin
        }
    }

    suspend fun saveAuthToken(authToken: String) {
        Log.d("auth", authToken.toString())
        dataStore.edit { preferences ->
            preferences[PreferencesKey.authToken] = "Token $authToken"
        }
    }

    suspend fun saveUsername(username:String){
        dataStore.edit { preferences ->
            preferences[PreferencesKey.username] = username
        }
    }
    suspend fun readAuthToken() : String
    {
        val dataStoreKey = preferencesKey<String>("authToken")
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey] ?: " "
    }

    val readFromDataStore: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
            } else {
                throw exception

            }

        }
        .map { preference ->

            val isLogin = preference[PreferencesKey.isLogin] ?: false
            isLogin

        }

    suspend fun readUserName() : String{
        val dataStoreKey = preferencesKey<String>("username")
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey] ?: " "
    }
}
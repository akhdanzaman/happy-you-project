package com.example.capstoneproject.utils


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.capstoneproject.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map {preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "session_token",
                preferences[TOKEN_KEY] ?: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiYzUyM2M4N2ItYWU4MS00ODk1LWEzNDctZTA5YTYwZDVjZGVhIiwiZXhwIjoxNzE4OTU3ODQ5fQ.mnIxPCjYrtSKIletUbnTWWWmJsq69QOeqLjY2_rKRWk",
                preferences[USERID_KEY] ?: "c523c87b-ae81-4895-a347-e09a60d5cdea",
                preferences[STATE_KEY] ?: false
            )
        }
    }


    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[TOKEN_KEY] = user.token
            preferences[USERID_KEY] = user.userId
            preferences[STATE_KEY] = user.isLogin
        }
    }


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private var NAME_KEY = stringPreferencesKey("name")
        private var TOKEN_KEY = stringPreferencesKey("token")
        private var USERID_KEY = stringPreferencesKey("userId")
        private var STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>) : UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}
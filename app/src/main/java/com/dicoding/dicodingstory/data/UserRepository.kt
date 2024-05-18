package com.dicoding.dicodingstory.data

import android.util.Log
import com.dicoding.dicodingstory.data.local.pref.UserModel
import com.dicoding.dicodingstory.data.local.pref.UserPreference
import com.dicoding.dicodingstory.data.remote.response.BasicResponse
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.data.remote.retrofit.ApiService
import com.dicoding.dicodingstory.util.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        wrapEspressoIdlingResource {
            userPreference.logout()
        }
    }

    suspend fun register(name: String, email: String, password: String): BasicResponse {
        try {
            return apiService.registerUser(name, email, password)
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
            throw e
        }
    }

    suspend fun login(email: String, password: String): UserModel {
        try {
            val loginResponse = apiService.login(email, password)
            val user = loginResponse.loginResult
            return UserModel(user.userId, user.name, user.token)
        } catch (e: Exception) {
            Log.e("Login Failed", e.toString())
            throw e
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}
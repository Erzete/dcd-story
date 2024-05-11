package com.dicoding.dicodingstory.data

import android.util.Log
import com.dicoding.dicodingstory.data.local.pref.UserModel
import com.dicoding.dicodingstory.data.local.pref.UserPreference
import com.dicoding.dicodingstory.data.remote.response.BasicResponse
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getAllStory(): List<StoryItem> {
        try {
            val response = apiService.getAllStory()
            return response.listStory
        } catch (e: HttpException) {
            Log.e("Get All Story API fetch failed", e.toString())
            throw e
        }
    }

    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody): BasicResponse {
        try {
            return apiService.addNewStory(file, description)
        } catch (e: HttpException) {
            throw e
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}
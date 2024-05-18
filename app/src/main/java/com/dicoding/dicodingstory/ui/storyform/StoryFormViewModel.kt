package com.dicoding.dicodingstory.ui.storyform

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.remote.response.BasicResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryFormViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody, lat: RequestBody? = null, lon: RequestBody? = null) {
        try {
            val stories = storyRepository.uploadStory(file, description, lat, lon)
            Log.d("StoryFormViewModel", stories.message)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, BasicResponse::class.java)
            Log.e("StoryFormViewModel", errorResponse.message)
            throw e
        }
    }
}
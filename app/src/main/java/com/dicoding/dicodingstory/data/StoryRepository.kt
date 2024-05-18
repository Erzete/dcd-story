package com.dicoding.dicodingstory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.dicodingstory.data.local.pref.UserModel
import com.dicoding.dicodingstory.data.local.pref.UserPreference
import com.dicoding.dicodingstory.data.remote.pagingsource.StoryPagingSource
import com.dicoding.dicodingstory.data.remote.response.BasicResponse
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.data.remote.response.StoryResponse
import com.dicoding.dicodingstory.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryRepository private constructor(
    private val apiService: ApiService
) {
    fun getAllStory(): LiveData<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
//    suspend fun getAllStory(): List<StoryItem> {
//        try {
//            val response = apiService.getAllStory()
//            return response.listStory
//        } catch (e: HttpException) {
//            Log.e("Get All Story API fetch failed", e.toString())
//            throw e
//        }
//    }

    suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody): BasicResponse {
        try {
            return apiService.addNewStory(file, description)
        } catch (e: HttpException) {
            throw e
        }
    }

    suspend fun getStoriesWithLocation(): List<StoryItem> {
        try {
            val response = apiService.getStoriesWithLocation()
            return response.listStory
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
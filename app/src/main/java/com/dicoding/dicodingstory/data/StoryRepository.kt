package com.dicoding.dicodingstory.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.dicodingstory.data.local.room.database.StoryDatabase
import com.dicoding.dicodingstory.data.local.room.StoryRemoteMediator
import com.dicoding.dicodingstory.data.remote.response.BasicResponse
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import com.dicoding.dicodingstory.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryRepository private constructor(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
) {

    fun getAllStory(): LiveData<PagingData<StoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
//                StoryPagingSource(apiService)
            }
        ).liveData
    }

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
            storyDatabase: StoryDatabase,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(storyDatabase, apiService)
            }.also { instance = it }
    }
}
package com.dicoding.dicodingstory.di

import android.content.Context
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.UserRepository
import com.dicoding.dicodingstory.data.local.pref.UserPreference
import com.dicoding.dicodingstory.data.local.pref.dataStore
import com.dicoding.dicodingstory.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}
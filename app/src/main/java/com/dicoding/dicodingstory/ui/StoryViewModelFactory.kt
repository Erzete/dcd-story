package com.dicoding.dicodingstory.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.di.Injection
import com.dicoding.dicodingstory.ui.maps.MapsViewModel
import com.dicoding.dicodingstory.ui.story.StoryViewModel
import com.dicoding.dicodingstory.ui.storyform.StoryFormViewModel

class StoryViewModelFactory private constructor(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            return StoryViewModel(storyRepository) as T
        } else if (modelClass.isAssignableFrom(StoryFormViewModel::class.java)) {
            return StoryFormViewModel(storyRepository) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(Injection.provideStoryRepository(context))
            }.also { instance = it }
    }
}
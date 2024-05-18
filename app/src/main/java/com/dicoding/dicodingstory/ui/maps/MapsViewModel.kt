package com.dicoding.dicodingstory.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _listStory = MutableLiveData<List<StoryItem>>()
    val listStory: LiveData<List<StoryItem>> = _listStory

    init {
        getStoriesWithlocation()
    }
    private fun getStoriesWithlocation() {
        viewModelScope.launch {
            try {
                val stories = storyRepository.getStoriesWithLocation()
                _listStory.postValue(stories)
            } catch (e: Exception) {
                Log.e("MapsViewModel", "Failed Fetching Story")
            }
        }
    }
}
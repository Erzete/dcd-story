package com.dicoding.dicodingstory.ui.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.remote.response.StoryItem
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listStory = MutableLiveData<List<StoryItem>>()
    val listStory: LiveData<List<StoryItem>> = _listStory

    init {
        getAllStory()
    }

    private fun getAllStory() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val stories = storyRepository.getAllStory()
                _listStory.postValue(stories)
                Log.d("StoryViewModel", "berhasil")
            } catch (e: Exception) {
                Log.e("StoryViewModel", "Failed Fetching All Story")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}


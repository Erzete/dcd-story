package com.dicoding.dicodingstory.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.remote.response.StoryItem

class StoryViewModel(storyRepository: StoryRepository) : ViewModel() {
    val listStory: LiveData<PagingData<StoryItem>> =
        storyRepository.getAllStory().cachedIn(viewModelScope)

}


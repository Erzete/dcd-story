package com.dicoding.dicodingstory.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstory.data.StoryRepository
import com.dicoding.dicodingstory.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.register(name, email, password)
                _status.postValue(true)
            } catch (e: Exception) {
                _status.postValue(false)
                Log.e("RegisterViewModel", e.toString())
            }
        }
    }

}
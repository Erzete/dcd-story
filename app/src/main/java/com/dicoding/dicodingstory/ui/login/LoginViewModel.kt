package com.dicoding.dicodingstory.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstory.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.login(email, password)
                userRepository.saveSession(user)
                _status.postValue(true)
            } catch (e: Exception) {
                _status.postValue(false)
                Log.e("LoginViewModel", e.toString())
            }
        }
    }

}
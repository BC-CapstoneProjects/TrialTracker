package com.kinisi.trailtracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
//change 'user' to name after updated profile settings
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome Back, User!"
    }
    val text: LiveData<String> = _text
}
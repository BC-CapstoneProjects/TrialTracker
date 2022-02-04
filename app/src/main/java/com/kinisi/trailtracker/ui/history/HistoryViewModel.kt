package com.kinisi.trailtracker.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel {
    class HistoryViewModel : ViewModel() {

        private val _text = MutableLiveData<String>().apply {
            value = "This is history Fragment"
        }
        val text: LiveData<String> = _text
    }
}
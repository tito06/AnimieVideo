package com.prabal.animatorlearn.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prabal.animatorlearn.viewmodels.AnimieDetailViewModel

class AnimieDetailViewModelFactory(private val animeId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimieDetailViewModel::class.java)) {
            return AnimieDetailViewModel(animeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

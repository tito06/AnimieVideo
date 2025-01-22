package com.prabal.animatorlearn.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabal.animatorlearn.models.AnimieDetailModel
import com.prabal.animatorlearn.network.ApiInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AnimieDetailViewModel(private val id:String): ViewModel() {

    private val apiService = ApiInterface.create()


    private val _animieDetail = MutableStateFlow<AnimieDetailModel?>(null)
    val animieDetail = _animieDetail

    private val _error = MutableStateFlow<String?>(null)
    val errorState = _error

    private val _loading = MutableStateFlow(false)
    val loadingState = _loading

    init {

        fetchAnimieDetails(id)
    }

      fun fetchAnimieDetails(anime_id: String){

          if (anime_id.isBlank()) {
              _error.value = "Anime ID is required"
              return
          }

          _loading.value = true
          viewModelScope.launch {
                try {
                    val response = apiService.getAnimieDetails(anime_id)
                    if (response.isSuccessful && response.body() != null) {
                        _animieDetail.value = response.body()!!
                        _error.value = null
                    }else{
                        _error.value = response.errorBody()?.string()
                    }
                } catch (e: Exception) {
                    _error.value = e.message

                }finally {
                    _loading.value = false
                }
            }


    }
}
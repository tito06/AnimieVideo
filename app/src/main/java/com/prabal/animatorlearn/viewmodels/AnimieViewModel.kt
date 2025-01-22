package com.prabal.animatorlearn.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabal.animatorlearn.models.AnimieModel
import com.prabal.animatorlearn.network.ApiInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AnimieViewModel: ViewModel() {

    private  val apiService = ApiInterface.create()

    private  val _animeList=  MutableStateFlow<List<AnimieModel>>(emptyList())
    val animeList = _animeList

    private val _error = MutableStateFlow<String?>(null)
    val errorState = _error

    init {

        fetchAnimieList()
    }

    fun fetchAnimieList(){
        viewModelScope.launch {
            try {

                val response = apiService.getAnimeList()
                if(response.isSuccessful && response.body() != null){
                    _animeList.value = listOf(response.body()!!)
                }else{
                    _error.value = response.errorBody()?.string()
                }
                }catch (e: Exception){

                    _error.value = e.message
                }

            }
        }
    }

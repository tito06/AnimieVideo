package com.prabal.animatorlearn.network

import com.prabal.animatorlearn.models.AnimieDetailModel
import com.prabal.animatorlearn.models.AnimieModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {


    @GET("top/anime")
    suspend fun getAnimeList(): Response<AnimieModel>

    @GET("anime/{anime_id}")
    suspend fun getAnimieDetails(@Path("anime_id") anime_id:String):Response<AnimieDetailModel>



    companion object {

        private const val baseUrl = "https://api.jikan.moe/v4/"

        fun create(): ApiInterface{
            return  Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)

        }
    }

}
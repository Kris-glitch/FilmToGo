package com.movieapp.filmtogo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    companion object{

        private val retrofit = getRetrofitInstance()
        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getMovieApi() : Api{
            val api : Api = retrofit.create(Api::class.java)
            return api
        }
    }
}
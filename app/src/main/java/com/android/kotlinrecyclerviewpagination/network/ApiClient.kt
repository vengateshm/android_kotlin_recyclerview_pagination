package com.android.kotlinrecyclerviewpagination.network

import com.android.kotlinrecyclerviewpagination.network.api.MoviesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // object keyword for singleton instance
    private const val BASE_URL: String = "https://api.themoviedb.org/3/"

    private var retrofit: Retrofit

    //Apis
    private lateinit var moviesApi: MoviesApi

    init {
        // Init block used to initialize after object creation
        retrofit = initRetrofit()
    }

    private fun initRetrofit(): Retrofit {
        // Set logging level
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // Add logger to http client using builder object
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(loggingInterceptor)

        val okHttpClient = okHttpBuilder.build()

        // Add Rxjava call adapter factory
        // and gson object converter factory
        val retrofitInstance = Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        moviesApi = retrofitInstance.create(MoviesApi::class.java)

        return retrofitInstance
    }

    fun getMoviesApi(): MoviesApi {
        return moviesApi
    }
}
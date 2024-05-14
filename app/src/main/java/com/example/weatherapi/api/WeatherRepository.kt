package com.example.weatherapi.api

import com.example.weatherapi.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
//class WeatherRepository {
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://api.openweathermap.org/data/2.5/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val service = retrofit.create(WeatherService::class.java)
//
//    fun getWeather(city: String, apiKey: String, callback: Callback<WeatherResponse>) {
//        val call = service.getWeather(city, apiKey)
//        call.enqueue(callback)
//    }
//}
object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val api: WeatherService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}

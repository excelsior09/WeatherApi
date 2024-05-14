package com.example.weatherapi.api

import com.example.weatherapi.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}

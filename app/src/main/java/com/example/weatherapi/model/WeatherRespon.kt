package com.example.weatherapi.model

data class WeatherResponse(
    val list: List<WeatherInfo>,
    val city: CityInfo
)

data class WeatherInfo(
    val dt_txt: String,
    val main: MainInfo,
    val weather: List<WeatherDetail>,
    val wind: WindInfo
)

data class MainInfo(
    val temp: Float,
    val humidity: Int,
    val pressure: Int
)

data class WeatherDetail(
    val main: String,
    val description: String
)

data class WindInfo(
    val speed: Float,
    val deg: Int
)

data class CityInfo(
    val name: String,
    val country: String
)
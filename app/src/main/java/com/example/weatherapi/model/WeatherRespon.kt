package com.example.weatherapi.model

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
)

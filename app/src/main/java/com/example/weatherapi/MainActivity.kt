package com.example.weatherapi


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapi.api.WeatherRepository
import android.widget.Toast
import com.example.weatherapi.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "e0289d6d55b9f0e7039f5604f8b9a38f"
    private val weatherRepository = WeatherRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityName = "Jakarta"

        weatherRepository.getWeather(cityName, apiKey, object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherResponse = response.body()
                weatherResponse?.let {
                    val weatherDescription = it.weather.firstOrNull()?.description ?: "Unknown"
                    val temperature = it.main.temp.toString()

                    val textViewWeatherDescription = findViewById<TextView>(R.id.textViewWeatherDescription)
                    val textViewTemperature = findViewById<TextView>(R.id.textViewTemperature)

                    textViewWeatherDescription.text = "Weather: $weatherDescription"
                    textViewTemperature.text = "Temperature: $temperature"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
                showToast("Failed to get weather data. Please check your internet connection.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

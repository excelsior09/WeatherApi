package com.example.weatherapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapi.api.OpenWeatherApi
import com.example.weatherapi.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), WeatherView {

    private lateinit var presenter: WeatherPresenter
    private lateinit var textViewWeather: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewWeather = findViewById(R.id.textViewWeather)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OpenWeatherApi::class.java)
        val repository = WeatherRepository(service)
        presenter = WeatherPresenter(this, repository)

        presenter.getWeather("Jakarta")
    }

    override fun showWeather(weatherResponse: WeatherResponse) {
        val cityName = weatherResponse.city.name
        val country = weatherResponse.city.country
        val stringBuilder = StringBuilder()
        stringBuilder.append("Current weather in $cityName, $country:\n\n")

        for (weatherInfo in weatherResponse.list) {
            val dateTime = weatherInfo.dt_txt
            val temperature = weatherInfo.main.temp
            val humidity = weatherInfo.main.humidity
            val weatherDescription = weatherInfo.weather[0].description
            val windSpeed = weatherInfo.wind.speed

            stringBuilder.append("Date and Time: $dateTime\n")
            stringBuilder.append("Temperature: $temperature °C\n")
            stringBuilder.append("Humidity: $humidity %\n")
            stringBuilder.append("Weather: $weatherDescription\n")
            stringBuilder.append("Wind Speed: $windSpeed m/s\n")
            stringBuilder.append("------------------------------------------\n")
        }

        textViewWeather.text = stringBuilder.toString()
    }

    override fun showError() {
        textViewWeather.text = "Failed to fetch weather data"
    }
}

interface WeatherView {
    fun showWeather(weatherResponse: WeatherResponse)
    fun showError()
}

class WeatherPresenter(
    private val view: WeatherView,
    private val repository: WeatherRepository
) {
    fun getWeather(city: String) {
        repository.getWeather(city, object : WeatherCallback {
            override fun onSuccess(weatherResponse: WeatherResponse) {
                view.showWeather(weatherResponse)
            }

            override fun onFailure() {
                view.showError()
            }
        })
    }
}

interface WeatherCallback {
    fun onSuccess(weatherResponse: WeatherResponse)
    fun onFailure()
}

class WeatherRepository(private val service: OpenWeatherApi) {
    fun getWeather(city: String, callback: WeatherCallback) {
        val apiKey = "e0289d6d55b9f0e7039f5604f8b9a38f"
        val call = service.getCurrentWeather(city, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let { callback.onSuccess(it) }
                } else {
                    callback.onFailure()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                callback.onFailure()
            }
        })
    }
}
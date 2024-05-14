package com.example.weatherapi

import android.net.http.HttpException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresExtension
import com.example.weatherapi.databinding.ActivityMainBinding
import com.example.weatherapi.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGetWeather.setOnClickListener {
            val cityName = binding.editTextCityName.text.toString()
            if (cityName.isNotBlank()) {
                getCurrentWeather(cityName)
            } else {
                showToast("Please enter a city name.")
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getCurrentWeather(cityName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(cityName, "36d38ea2926714eeda638f01b699af0e")
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    showToast("Failed to get weather data. Please check your internet connection.")
                }
                return@launch
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    showToast("HttpException: ${e.message}")
                }
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val weatherResponse = response.body()!!
                    binding.textView.text = "Kota: ${weatherResponse.name}"
                    binding.textView2.text = "Cuaca: ${weatherResponse.weather[0].main}"
                    binding.textView3.text = "Deskripsi: ${weatherResponse.weather[0].description}"
                    binding.textView4.text = "Temperature: ${weatherResponse.main.temp}°C"
                }
            } else {
                withContext(Dispatchers.Main) {
                    showToast("Failed to get weather data.")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

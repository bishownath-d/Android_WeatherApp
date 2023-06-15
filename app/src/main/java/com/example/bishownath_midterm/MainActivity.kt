package com.example.bishownath_midterm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bishownath_midterm.databinding.ActivityMainBinding
import com.example.bishownath_midterm.model.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = binding.editTextCity.text

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.button.setOnClickListener {
            viewModel.fetchData(city.toString())
            Log.d("MyTag", "Hello ${city} ")
        }

        viewModel.weatherData.observe(this) { weather ->
            Log.d("MyTag", weather.toString())
            updateUI(weather)
        }
    }

    private fun updateUI(weatherData: List<WeatherData>) {
        val index = binding.editTextCity.text.toString().toIntOrNull() ?: 0
        if (index in weatherData.indices) {
            val post = weatherData[index]
            binding.province.text = "City: ${post.address}"
            binding.tvLocation.text = "Location: ${post.resolvedAddress}"
            binding.tvCurrent.text = "Current Temp: ${post.currentConditions.temp.toString()}"
            binding.tvMax.text = "Max Temp: ${post.days[index].tempmax}"
            binding.tvMin.text = "Min Temp: ${post.days[index].tempmin}"
            binding.tvPrecip.text = "POP: ${post.currentConditions.precipprob.days.inWholeHours}"
            binding.tvDescrip.text = "Desc: ${post.days[index].description}"
        }
    }
}
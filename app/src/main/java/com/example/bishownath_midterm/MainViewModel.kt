package com.example.bishownath_midterm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bishownath_midterm.model.WeatherData
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val API_KEY = "BNR9A4ND6HNMB3WX2RTE7R63Q"

class MainViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> = _weatherData

    private val job = SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }


    fun fetchData(city: String) {

        ioScope.launch {
            val url =
                URL("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${city}?unitGroup=metric&elements=datetime%2Ctempmax%2Ctempmin%2Ctemp%2Cprecipprob%2Cdescription&include=current&key=${API_KEY}&contentType=json")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, WeatherData::class.java)
                _weatherData.postValue(listOf(request))
                Log.d("MyTag", request.address)
                inputStreamReader.close()
                inputSystem.close()
            } else {
                Log.d("MyTag", "Error Fetching")
            }
        }.start()
    }
}
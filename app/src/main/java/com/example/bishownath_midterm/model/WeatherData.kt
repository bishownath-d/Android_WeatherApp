package com.example.bishownath_midterm.model

data class WeatherData(
    val address: String,
    val currentConditions: CurrentConditions,
    val days: List<Day>,
    val latitude: Double,
    val longitude: Double,
    val queryCost: Int,
    val resolvedAddress: String,
    val timezone: String,
)
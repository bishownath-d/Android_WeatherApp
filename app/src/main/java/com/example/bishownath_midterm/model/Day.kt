package com.example.bishownath_midterm.model

data class Day(
    val datetime: String,
    val description: String,
    val precipprob: Double,
    val temp: Double,
    val tempmax: Double,
    val tempmin: Double
)
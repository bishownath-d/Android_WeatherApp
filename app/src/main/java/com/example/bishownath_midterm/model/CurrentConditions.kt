package com.example.bishownath_midterm.model

data class CurrentConditions(
    val datetime: String,
    val precipprob: Int,
    val temp: Double
)
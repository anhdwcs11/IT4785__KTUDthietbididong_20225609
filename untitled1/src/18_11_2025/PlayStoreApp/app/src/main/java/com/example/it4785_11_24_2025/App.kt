package com.example.it4785_11_24_2025

data class App(
    val name: String,
    val icon: Int,
    val category: String? = null,
    val rating: Float? = null,
    val size: String? = null,
    val isVertical: Boolean = false
)
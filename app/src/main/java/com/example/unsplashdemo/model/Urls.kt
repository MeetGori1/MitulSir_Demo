package com.example.unsplashdemo.model


data class ImageItem(
    val description: String,
    val id: String,
    val urls: Urls,
    val errors: String
)
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class SearchItem(
    val results: List<ImageItem>
)

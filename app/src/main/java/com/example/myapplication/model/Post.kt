package com.example.myapplication.model

data class Post(
    val id: String,
    val title: String,
    val language: String,
    val thumbnail: ThumbNail,
    val mediaType: Int,
    val coverageURL: String,
    val publishedAt: String,
    val publishedBy: String
)

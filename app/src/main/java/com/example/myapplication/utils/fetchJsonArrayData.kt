package com.example.myapplication.utils

import com.example.myapplication.model.Post
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


fun fetchJsonArrayData(url: String): List<Post>? {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body?.string()
        return if (responseBody != null) {
            val listType = object : TypeToken<List<Post>>() {}.type
            Gson().fromJson(responseBody, listType)
        } else {
            null
        }
    }
}
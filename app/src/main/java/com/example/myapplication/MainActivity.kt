package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.utils.fetchJsonArrayData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageUrls = mutableListOf<String>()
        val url = "https://acharyaprashant.org/api/v2/content/misc/media-coverages?limit=100"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Fetch the data in a coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val posts = fetchJsonArrayData(url)
                withContext(Dispatchers.Main) {
                    if (posts != null) {
                        posts.forEach { post ->
                            run {
                                val domain = post.thumbnail.domain;
                                val basePath = post.thumbnail.basePath;
                                val key = post.thumbnail.key;
                                val localUrl =
                                    domain.plus("/").plus(basePath).plus("/0/").plus(key);
                                imageUrls.add(localUrl)
                            }
                        }
                        recyclerView.adapter = ImageAdapter(imageUrls)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to fetch posts data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}
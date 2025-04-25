package com.example.giphy_app_kotlin

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GifAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private var currentPage = 0
    private val pageSize = 25
    private var currentQuery = ""
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchEditText = findViewById(R.id.searchEditText)

        adapter = GifAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadTrendingGifs()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentPage = 0
                currentQuery = s.toString()
                adapter.clear()
                if (currentQuery.isEmpty()) loadTrendingGifs() else loadSearchGifs()
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastVisibleItemPosition() >= adapter.itemCount - 5) {
                    if (currentQuery.isEmpty()) loadTrendingGifs() else loadSearchGifs()
                }
            }
        })
    }

    private fun loadTrendingGifs() {
        isLoading = true
        GiphyApi.retrofitService.getTrendingGifs(GiphyApi.API_KEY, pageSize, currentPage * pageSize)
            .enqueue(object : Callback<GiphyResponse> {
                override fun onResponse(call: Call<GiphyResponse>, response: Response<GiphyResponse>) {
                    response.body()?.let {
                        adapter.addGifs(it.data)
                        currentPage++
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<GiphyResponse>, t: Throwable) {
                    isLoading = false
                }
            })
    }

    private fun loadSearchGifs() {
        isLoading = true
        GiphyApi.retrofitService.searchGifs(GiphyApi.API_KEY, currentQuery, pageSize, currentPage * pageSize)
            .enqueue(object : Callback<GiphyResponse> {
                override fun onResponse(call: Call<GiphyResponse>, response: Response<GiphyResponse>) {
                    response.body()?.let {
                        adapter.addGifs(it.data)
                        currentPage++
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<GiphyResponse>, t: Throwable) {
                    isLoading = false
                }
            })
    }
}



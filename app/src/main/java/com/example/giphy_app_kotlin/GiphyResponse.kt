package com.example.giphy_app_kotlin
data class GiphyResponse(val data: List<GifData>)
data class GifData(val images: GifImages)
data class GifImages(val fixed_height: GifUrl)
data class GifUrl(val url: String)
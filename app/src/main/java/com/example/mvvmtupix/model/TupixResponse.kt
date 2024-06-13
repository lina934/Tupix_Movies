package com.example.tupix.api

import com.google.gson.annotations.SerializedName

data class TupixResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
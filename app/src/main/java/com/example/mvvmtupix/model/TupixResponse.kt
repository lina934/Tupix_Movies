package com.example.mvvmtupix.model

import com.example.mvvmtupix.model.Movie
import com.google.gson.annotations.SerializedName

data class TupixResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
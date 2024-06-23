package com.example.mvvmtupix.model

import com.example.mvvmtupix.model.Cast
import com.example.mvvmtupix.model.Crew

data class TupixDetailsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
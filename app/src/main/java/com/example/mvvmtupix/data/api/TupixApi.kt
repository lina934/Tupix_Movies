package com.example.mvvmtupix.data.api

import com.example.mvvmtupix.model.TupixDetailsResponse
import com.example.mvvmtupix.model.TupixResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TupixApi {
    @GET("movie/top_rated")
    suspend fun getTopTupix(
        @Query("api_key") api_key : String,
        @Query("page") page : Int
    ): Response<TupixResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key : String,
        @Query("page") page : Int
    ): Response<TupixResponse>

    @GET("movie/upcoming")
    suspend fun getnewmovies(
        @Query("api_key") api_key : String,
        @Query("page") page : Int
    ): Response<TupixResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCustomList(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key : String,
    ): Response<TupixDetailsResponse>

    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") query : String,
        @Query("api_key") api_key : String,
        @Query("page") page : Int
    ): Response<TupixResponse>
}
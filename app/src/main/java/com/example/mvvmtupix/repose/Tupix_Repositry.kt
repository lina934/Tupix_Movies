package com.example.mvvmtupix.repose

import com.example.mvvmtupix.data.api.RetrofitInstanse
import com.example.mvvmtupix.data.local.TupixDao
import com.example.tupix.api.Movie
import com.example.tupix.api.Tuoix_Details_Response
import com.example.tupix.api.TupixResponse
import retrofit2.Response

class Tupix_Repositry(private val dao : TupixDao) {
    suspend fun getUpMovies(api: String, page: Int): Response<TupixResponse>{
        return RetrofitInstanse.api.getTopTupix(api, page)
    }

    suspend fun getDownMovies(api: String, page: Int):Response<TupixResponse>{
        return RetrofitInstanse.api.getPopular(api,page)
    }

    suspend fun getNewsMovies(api: String, page: Int):Response<TupixResponse>{
        return RetrofitInstanse.api.getnewmovies(api,page)
    }

    suspend fun getCustomMovies(id: Int, api: String):Response<Tuoix_Details_Response>{
        return RetrofitInstanse.api.getCustomList(id,api)
    }

    suspend fun getSearchMovies(query:String,api: String, page: Int):Response<TupixResponse>{
        return RetrofitInstanse.api.getMovieSearch(query,api,page)
    }

    suspend fun getInsertMovies(movie: Movie){
        return dao.insertMovies(movie)
    }

    suspend fun getFavMovies():List<Movie>{
        return dao.getAllFavouriteMovies()
    }

    suspend fun isExisted(movie_id : Int):Boolean{
        return dao.isExisted(movie_id)
    }

    suspend fun deleteMovies(movie_id: Int){
        dao.deleteMovies(movie_id)
    }


}
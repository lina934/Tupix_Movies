package com.example.mvvmtupix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.model.Movie
import com.example.mvvmtupix.model.TupixDetailsResponse
import com.example.mvvmtupix.model.TupixResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(val repo: Tupix_Repositry) : ViewModel() {

    private var __tupixCustomList = MutableLiveData<TupixDetailsResponse>()
    val tupixList: LiveData<TupixDetailsResponse>
        get() = __tupixCustomList

    private var __tupixNewsList = MutableLiveData<TupixResponse>()
    val tupixNews: LiveData<TupixResponse>
        get() = __tupixNewsList

    private var __checkExistence = MutableLiveData<Boolean>()
    val checkExistence: LiveData<Boolean>
        get() = __checkExistence

    fun insertToFav(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getInsertMovies(movie)

            __checkExistence.postValue(repo.isExisted(movie.id!!))

        }
    }

    fun deleteMovie(movie_id: Int) {
        viewModelScope.launch {
            repo.deleteMovies(movie_id)

            __checkExistence.postValue(repo.isExisted(movie_id))
        }
    }


    fun checkFavMovies(id: Int) {
        viewModelScope.launch {
            var isExisted = repo.isExisted(id)
            __checkExistence.postValue(isExisted)
        }
    }

    fun callMoviesNewsFun(api: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var response = repo.getNewsMovies(api, page)
            if (response != null) {
                if (response.isSuccessful) {
                    __tupixNewsList.postValue(response.body())
                }
            }
        }
    }


    fun callMoviesCustomFun(id: Int, api: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var response = repo.getCustomMovies(id, api)
            if (response != null) {
                if (response.isSuccessful) {
                    __tupixCustomList.postValue(response.body())
                }
            }
        }
    }


}

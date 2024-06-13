package com.example.mvvmtupix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.tupix.api.Movie
import kotlinx.coroutines.launch

class FavouriteFragmentViewModel(val repo : Tupix_Repositry):ViewModel(){
    private var __favMovies = MutableLiveData<List<Movie>>()
    val favMovies : LiveData<List<Movie>>
        get() = __favMovies

    fun getFavMovies(){
        viewModelScope.launch{
            __favMovies.postValue(repo.getFavMovies())
        }
    }
}

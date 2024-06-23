package com.example.mvvmtupix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.model.TupixResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesFragmentViewModel(val repo : Tupix_Repositry): ViewModel() {


    private var __tupixMoviesUp = MutableLiveData<TupixResponse>()
    val tupixMoviesUp : LiveData<TupixResponse>
        get() = __tupixMoviesUp

    private var __tupixMoviesDown = MutableLiveData<TupixResponse>()
    val tupixMoviesDown : LiveData<TupixResponse>
        get() = __tupixMoviesDown


    fun callMoviesUpFun(api : String,page : Int){
        viewModelScope.launch(Dispatchers.IO){
            var response = repo.getUpMovies(api,page)
            if (response != null){
                if (response.isSuccessful){
                    __tupixMoviesUp.postValue(response.body())
                }
            }
        }

    }

    fun callMoviesDownFun(api : String,page : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var response = repo.getDownMovies(api, page)
            if (response != null) {
                if (response.isSuccessful) {
                    __tupixMoviesDown.postValue(response.body())
                }
            }
        }
    }}
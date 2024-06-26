package com.example.mvvmtupix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.model.TupixResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragmentViewModel(val repo : Tupix_Repositry):ViewModel() {

    private var __tupixSearch = MutableLiveData<TupixResponse>()
    val tupixSearch : LiveData<TupixResponse>
        get() = __tupixSearch

    fun callSearchFun(query:String,api:String,page: Int){
        viewModelScope.launch(Dispatchers.IO){
            var response = repo.getSearchMovies(query, api, page)
            if (response != null){
                if (response.isSuccessful){
                    __tupixSearch.postValue(response.body())
                }
            }
        }
    }
}
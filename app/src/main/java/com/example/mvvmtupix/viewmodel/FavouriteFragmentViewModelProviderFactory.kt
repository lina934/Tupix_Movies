package com.example.mvvmtupix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtupix.repose.Tupix_Repositry

class FavouriteFragmentViewModelProviderFactory(val repo : Tupix_Repositry):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteFragmentViewModel(repo) as T
    }
}
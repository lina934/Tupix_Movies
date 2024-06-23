package com.example.mvvmtupix.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvmtupix.databinding.FragmentFavouriteBinding
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.viewmodel.FavouriteFragmentViewModel
import com.example.mvvmtupix.viewmodel.FavouriteFragmentViewModelProviderFactory
import com.example.mvvmtupix.adapters.TupixAdapterClm
import com.example.mvvmtupix.data.local.TupixDatabase

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val database by lazy { TupixDatabase.getAppDataBase(requireContext()) }
    private val repo by lazy { Tupix_Repositry(database.movieDao()) }
    private lateinit var viewModel: FavouriteFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val favouriteFragmentViewModelProviderFactory = FavouriteFragmentViewModelProviderFactory(repo)
        viewModel = ViewModelProvider(
            this,
            favouriteFragmentViewModelProviderFactory
        )[FavouriteFragmentViewModel::class.java]
        // Inflate the layout for this fragment

        viewModel.getFavMovies()
        subScribeFav()

        binding.backe.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    fun subScribeFav() {
        viewModel.favMovies.observe(viewLifecycleOwner, Observer { movieList ->
            Log.e("testFAV", movieList.toString())
            binding.favoritesRecycler.apply {
                adapter = TupixAdapterClm(requireContext(), {movie->
                    Log.d("FavouriteFragment", "Clicked on: ${movie.title}")
                    val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(movie)
                    findNavController().navigate(action)

                }, movieList)
            }
        })
    }
}
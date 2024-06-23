package com.example.mvvmtupix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvmtupix.databinding.FragmentMoviesBinding
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.viewmodel.MoviesFragmentViewModel
import com.example.mvvmtupix.viewmodel.MoviesFragmentViewModelProviderFactory
import com.example.mvvmtupix.adapters.TupixAdapter
import com.example.mvvmtupix.adapters.TupixAdapterClm
import com.example.mvvmtupix.data.local.TupixDatabase
import com.example.mvvmtupix.model.Movie


class MoviesFragment : Fragment() {
    lateinit var binding: FragmentMoviesBinding
    lateinit var viewModel: MoviesFragmentViewModel
    val database by lazy { TupixDatabase.getAppDataBase(requireContext()) }
    val repo by lazy {Tupix_Repositry(database.movieDao())}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater,container,false)

        binding.paginationProgressBar.visibility = View.VISIBLE
        var moviesFragmentViewModelProviderFactory = MoviesFragmentViewModelProviderFactory(repo)
        viewModel = ViewModelProvider(this,moviesFragmentViewModelProviderFactory)[MoviesFragmentViewModel::class.java]
        viewModel.callMoviesUpFun("e76112b72c6c245384a5ecfd814a3ec2",12)
        viewModel.callMoviesDownFun("e76112b72c6c245384a5ecfd814a3ec2",5)
        setUpMovies()
        setDownMovies()
        searchClick()
        binding.paginationProgressBar.visibility = View.INVISIBLE

        // Inflate the layout for this fragment
        return binding.root
    }
    fun setUpMovies(){
        viewModel.tupixMoviesUp.observe(viewLifecycleOwner, Observer {
            binding.recyclerHorizontal.apply {
                adapter = TupixAdapter(context,
                    onItemClick = {
                        val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(it)
                        findNavController().navigate(action)
                    }
                    ,it.movies as ArrayList<Movie>)
            }
        })
    }
    fun setDownMovies(){
        viewModel.tupixMoviesDown.observe(viewLifecycleOwner, Observer {
            binding.recyclerVertical.apply {
                adapter = TupixAdapterClm(context,
                    onItemClick ={
                        val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(it)
                        findNavController().navigate(action)
                    },
                    it.movies as ArrayList<Movie>)
            }
        })
        binding.favMovies.setOnClickListener {
            var action = MoviesFragmentDirections.actionMoviesFragmentToFavouriteFragment()
            findNavController().navigate(action)
        }
    }

    fun searchClick(){
        binding.searchicon.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToSearchFragment()
            findNavController().navigate(action)}
    }
}
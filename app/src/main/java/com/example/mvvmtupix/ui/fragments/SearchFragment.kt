package com.example.mvvmtupix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.mvvmtupix.viewmodel.SearchFragmentViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvmtupix.databinding.FragmentSearchBinding
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.viewmodel.SearchFragmentViewModelProviderFactory
import com.example.mvvmtupix.adapters.TupixAdapterClm
import com.example.mvvmtupix.data.local.TupixDatabase
import com.example.mvvmtupix.model.Movie

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchFragmentViewModel
    val database by lazy { TupixDatabase.getAppDataBase(requireContext()) }
    val repo by lazy {Tupix_Repositry(database.movieDao())}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)



        binding.searchIcon.setOnClickListener {
            if (binding.searchedittext.text.isEmpty()) {
                Toast.makeText(context, "fill it please", Toast.LENGTH_SHORT).show()
            } else {
                val searchFragmentViewModelProviderFactory = SearchFragmentViewModelProviderFactory(repo)
                viewModel = ViewModelProvider(
                    this,
                    searchFragmentViewModelProviderFactory
                )[SearchFragmentViewModel::class.java]
                viewModel.callSearchFun(
                    binding.searchedittext.text.toString(),
                    "e76112b72c6c245384a5ecfd814a3ec2",
                    5
                )
                setUpSearch()
            }
        }

        return binding.root
    }

    private fun setUpSearch() {
        viewModel.tupixSearch.observe(viewLifecycleOwner, Observer {
            binding.recyclersearch.apply {
                adapter = TupixAdapterClm(
                    context,
                    onItemClick = {
                        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it)
                        findNavController().navigate(action)
                    },
                    it.movies as ArrayList<Movie>
                )
            }
        })
    }
}

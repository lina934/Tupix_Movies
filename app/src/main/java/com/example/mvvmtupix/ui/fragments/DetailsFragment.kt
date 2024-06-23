package com.example.mvvmtupix.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvvmtupix.R
import com.example.mvvmtupix.databinding.FragmentDetailsBinding
import com.example.mvvmtupix.repose.Tupix_Repositry
import com.example.mvvmtupix.viewmodel.DetailsFragmentViewModel
import com.example.mvvmtupix.viewmodel.DetailsFragmentViewModelProviderFactory
import com.example.mvvmtupix.model.Movie
import com.example.mvvmtupix.adapters.TupixAdapter
import com.example.mvvmtupix.data.local.TupixDatabase
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: DetailsFragmentViewModel

    val database by lazy { TupixDatabase.getAppDataBase(requireContext()) }
    val repo by lazy { Tupix_Repositry(database.movieDao()) }
    var found = false

    val args: DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        var detailsFragmentViewModelProviderFactory = DetailsFragmentViewModelProviderFactory(repo)

        viewModel = ViewModelProvider(
            this,
            detailsFragmentViewModelProviderFactory
        )[DetailsFragmentViewModel::class.java]

        viewModel.callMoviesCustomFun(args.argsDetails.id!!, "e76112b72c6c245384a5ecfd814a3ec2")

        viewModel.callMoviesNewsFun("e76112b72c6c245384a5ecfd814a3ec2", page = 5)



        viewModel.checkFavMovies(args.argsDetails.id!!)

        setUpDetails()
        setDownDetails()
        subScribeIsExisted()

        binding.share.setOnClickListener {
            sharebtn()
        }
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.favMovies.setOnClickListener {
            if (found) {
                viewModel.deleteMovie(args.argsDetails.id!!)
            } else {
                viewModel.insertToFav(args.argsDetails)
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun setUpDetails() {
        viewModel.tupixList.observe(viewLifecycleOwner, Observer {

            binding.writerdetails.text = it.crew[0].name


            binding.ratedetails.text = args.argsDetails.vote_average.toString()
            binding.ratecount.text = "(" + args.argsDetails.vote_count.toString() + ")"
            Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + args.argsDetails.poster_path)
                .into(binding.imagedetails)
        }
        )
    }

    fun setDownDetails() {
        viewModel.tupixNews.observe(viewLifecycleOwner, Observer {
            binding.recyclerCustom.apply {
                adapter = TupixAdapter(
                    context,
                    onItemClick = {
                        val action =
                            FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(it)
                        findNavController().navigate(action)
                    },
                    it.movies as ArrayList<Movie>
                )
            }
        })
    }

    fun sharebtn() {
        val intent = Intent(Intent.ACTION_SEND)
        // Set the type of data to be shared
        intent.type = "text/plain"
        // Put the URL of the movie as the text to be shared
        intent.putExtra(
            Intent.EXTRA_TEXT,
            args.argsDetails.overview
        )
        startActivity(Intent.createChooser(intent, "Share via Whatsapp"))
    }

    fun subScribeIsExisted() {
        viewModel.checkExistence.observe(viewLifecycleOwner, Observer { value ->
            if (value) {
                found = true
                binding.favMovies.setImageResource(R.drawable.heart_red)
            } else {
                binding.favMovies.setImageResource(R.drawable.heart_empty)

            }
        })
    }

}
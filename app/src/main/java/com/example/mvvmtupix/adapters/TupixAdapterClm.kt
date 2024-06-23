package com.example.mvvmtupix.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtupix.databinding.ListTupixItemClmBinding
import com.squareup.picasso.Picasso
import com.example.mvvmtupix.model.Movie

class TupixAdapterClm(
    var context: Context,
    var onItemClick: ((Movie) -> Unit),
    var moviesList: List<Movie>
) : RecyclerView.Adapter<TupixAdapterClm.ViewHolder>() {


    class ViewHolder(var binding: ListTupixItemClmBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListTupixItemClmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentMovie = moviesList[position]


        holder.binding.titlemovie.text = currentMovie.title
        holder.binding.rate.text = currentMovie.vote_average.toString()
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" + currentMovie.poster_path)
            .into(holder.binding.imagedown)
        holder.binding.ratecount.text = "(" + currentMovie.vote_count.toString() + ")"
        holder.binding.carddown.setOnClickListener {
            onItemClick.invoke(currentMovie)
        }
    }


}
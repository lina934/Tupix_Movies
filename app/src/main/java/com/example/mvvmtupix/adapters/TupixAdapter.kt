package com.example.tupix

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtupix.databinding.ListTupixItemRowBinding
import com.squareup.picasso.Picasso
import com.example.tupix.api.Movie


class TupixAdapter (
    private var context: Context,
    var onItemClick: ((Movie) -> Unit),
    private var moviesListUp: List<Movie>
) : RecyclerView.Adapter<TupixAdapter.ViewHolder>() {


    class ViewHolder(var binding: ListTupixItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListTupixItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = moviesListUp.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentMovieup = moviesListUp[position]

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/" +currentMovieup.poster_path).into(holder.binding.imageup)

holder.binding.cardup.setOnClickListener{
    onItemClick.invoke(currentMovieup)
}
    }
}

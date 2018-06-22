package com.android.kotlinrecyclerviewpagination.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.kotlinrecyclerviewpagination.R
import com.android.kotlinrecyclerviewpagination.models.Movie
import com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders.LoadingVH
import com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders.MovieVH


class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1

    private var moviesList: MutableList<Movie> = ArrayList()
    private var isLoadingAdded: Boolean = false

    fun setMovies(moviesList: MutableList<Movie>) {
        this.moviesList = moviesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val item = inflater.inflate(R.layout.movie_list_item, parent, false)
                MovieVH(item)
            }
            LOADING -> {
                val item = inflater.inflate(R.layout.load_more_progress, parent, false)
                LoadingVH(item)
            }
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == moviesList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val movie = moviesList[position]
                (holder as MovieVH).setMovie(movie)
            }
            LOADING -> {
            }
        }
    }

    fun add(r: Movie) {
        moviesList.add(r)
        notifyItemInserted(moviesList.size - 1)
    }

    fun addAll(moveMovies: List<Movie>) {
        for (result in moveMovies) {
            add(result)
        }
    }

    fun remove(r: Movie?) {
        val position = moviesList.indexOf(r)
        if (position > -1) {
            moviesList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Movie())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = moviesList.size - 1
        val result = getItem(position)

        if (result != null) {
            moviesList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Movie? {
        return moviesList.get(position)
    }
}
package com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.kotlinrecyclerviewpagination.R
import com.android.kotlinrecyclerviewpagination.models.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MovieVH(itemView: View) : BaseVH(itemView) {
    private val BASE_URL_IMG: String = "https://image.tmdb.org/t/p/w150"

    private lateinit var ivMoviePoster: ImageView
    private lateinit var tvMovieYear: TextView
    private lateinit var tvMovieTitle: TextView
    private lateinit var tvMovieDescription: TextView
    private lateinit var progressBar: ProgressBar

    init {
        findViews(itemView)
    }

    private fun findViews(itemView: View) {
        ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster)
        tvMovieYear = itemView.findViewById(R.id.tvMovieYear)
        tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle)
        tvMovieDescription = itemView.findViewById(R.id.tvMovieDescription)
        progressBar = itemView.findViewById(R.id.progressBar)
    }

    fun setMovie(movie: Movie) {
        // Year only needed so take the substring
        tvMovieYear.text = movie.releaseDate.substring(0, 4) + "|" + movie.originalLanguage.toUpperCase()
        tvMovieTitle.text = movie.title
        tvMovieDescription.text = movie.overview

        Glide.with(getContext())
                .load(BASE_URL_IMG + movie.posterPath)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(ivMoviePoster)
    }
}
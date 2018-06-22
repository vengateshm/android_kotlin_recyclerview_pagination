package com.android.kotlinrecyclerviewpagination.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.kotlinrecyclerviewpagination.R
import com.android.kotlinrecyclerviewpagination.models.Movie
import com.android.kotlinrecyclerviewpagination.network.ApiClient
import com.android.kotlinrecyclerviewpagination.ui.adapters.MovieListAdapter
import com.android.kotlinrecyclerviewpagination.utils.recyclerView.PaginationScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    // lateinit can be applied only for var not val(since val is immutable)
    private lateinit var rvList: RecyclerView
    private lateinit var tvNoRecords: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var moviesDisposable: Disposable
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = 1
    private val totalPages: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        setRecyclerView()

        progressBar.visibility = View.VISIBLE
        getMovies(currentPage)
    }

    // Find views by ids
    private fun findViews() {
        rvList = findViewById(R.id.rvList)
        tvNoRecords = findViewById(R.id.tvNoRecords)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setRecyclerView() {
        val llm = LinearLayoutManager(this)
        rvList.layoutManager = llm

        rvList.addOnScrollListener(object : PaginationScrollListener(llm) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                getMovies(currentPage)
            }
        })

        rvList.setHasFixedSize(true)

        movieListAdapter = MovieListAdapter()
        rvList.adapter = movieListAdapter
    }

    private fun getMovies(currentPage: Int) {
        moviesDisposable = ApiClient.getMoviesApi()
                .getTopRatedMovies(getString(R.string.moviedb_api_key),
                        "en_US", currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { topRatedMovies ->
                            progressBar.visibility = View.GONE
                            setMovies(topRatedMovies.moviesList)
                        },
                        { throwable ->
                            progressBar.visibility = View.GONE
                            Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
                        })
    }

    private fun setMovies(moviesList: ArrayList<Movie>) {
        if (currentPage == 1) {
            movieListAdapter.setMovies(moviesList)
            movieListAdapter.addLoadingFooter()
            movieListAdapter.notifyDataSetChanged()
            if (currentPage == totalPages) {
                movieListAdapter.removeLoadingFooter()
                isLastPage = true
            }
        } else {
            if (!moviesList.isEmpty()) {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                movieListAdapter.addAll(moviesList)
                movieListAdapter.addLoadingFooter()
            } else {
                movieListAdapter.removeLoadingFooter()
                isLoading = false
                isLastPage = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!moviesDisposable.isDisposed) {
            moviesDisposable.dispose()
        }
    }
}

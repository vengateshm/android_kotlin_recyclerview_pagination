package com.android.kotlinrecyclerviewpagination.models

import com.google.gson.annotations.SerializedName

class Movie {

    @SerializedName("poster_path")
    val posterPath: String = ""
    @SerializedName("adult")
    val adult: Boolean = false
    @SerializedName("overview")
    val overview: String = ""
    @SerializedName("release_date")
    val releaseDate: String = ""
    @SerializedName("id")
    val id: Int = 0
    @SerializedName("original_title")
    val originalTitle: String = ""
    @SerializedName("original_language")
    val originalLanguage: String = ""
    @SerializedName("title")
    val title: String = ""
    @SerializedName("backdrop_path")
    val backdropPath: String = ""
    @SerializedName("popularity")
    val popularity: Double = 0.0
    @SerializedName("vote_count")
    val voteCount: Int = 0
    @SerializedName("video")
    val video: Boolean = false
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0
}

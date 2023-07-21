package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieList(
    val results: List<Movie>,
    val page: Int,
    val total_pages: Int
) : Parcelable

@Parcelize
data class Movie(
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val original_language: String,
    val adult: Boolean
) : Parcelable
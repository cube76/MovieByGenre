package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    val genres: List<Genre>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: String,
    val vote_average: Double,
    val videos: Video
) : Parcelable

@Parcelize
data class Video(
    val results: List<VideoResult>
) : Parcelable

@Parcelize
data class VideoResult(
    val type: String,
    val key: String
) : Parcelable
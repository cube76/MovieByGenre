package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("results")
    val results: List<MovieResult>,
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("total_pages")
    val total_pages: Int
)

data class MovieResult(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("original_title")
    val original_title: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val poster_path: String,

    @field:SerializedName("release_date")
    val release_date: String,

    @field:SerializedName("original_language")
    val original_language: String,

    @field:SerializedName("adult")
    val adult: Boolean
)


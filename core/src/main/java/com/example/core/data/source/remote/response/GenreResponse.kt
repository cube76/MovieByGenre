package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<Genre>
)

data class Genre(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)
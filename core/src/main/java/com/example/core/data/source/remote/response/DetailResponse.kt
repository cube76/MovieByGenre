package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @field:SerializedName("genres")
    val genres: List<Genre>
)
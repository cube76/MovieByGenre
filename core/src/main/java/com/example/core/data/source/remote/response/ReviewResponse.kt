package com.example.core.data.source.remote.response

import com.example.core.domain.model.ReviewResult
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @field:SerializedName("genres")
    val results: List<ReviewResult>
)
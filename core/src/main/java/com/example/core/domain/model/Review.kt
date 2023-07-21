package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  Review(
    val results: List<ReviewResult>,
    val id: Int,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
) : Parcelable

@Parcelize
data class ReviewResult(
    val author_details: AuthorDetail,
    val author: String,
    val content: String,
    val updated_at: String
) : Parcelable

@Parcelize
data class AuthorDetail(
    val avatar_path: String,
    val rating: Int
) : Parcelable
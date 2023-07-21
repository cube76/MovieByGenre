package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreList(
    val genres: List<Genre>
) : Parcelable

@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable
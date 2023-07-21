package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.*
import dagger.Binds
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(genre: String, page:Int): Flow<Resource<MovieList>>
    fun getAllGenre(): Flow<Resource<GenreList>>
    fun getDetailMovie(movie_id:Int, append_to_response:String): Flow<Resource<DetailMovie>>
    fun getReview(movie_id:Int, page:Int): Flow<Resource<Review>>
}
package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.DetailMovie
import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
    MovieUseCase {

    override fun getAllMovie(genre: String, page:Int) = movieRepository.getAllMovie(genre, page)
    override fun getAllGenre() = movieRepository.getAllGenre()
    override fun getDetailMovie(movie_id:Int, append_to_response:String) = movieRepository.getDetailMovie(movie_id, append_to_response)
    override fun getReview(movie_id:Int, page:Int) = movieRepository.getReview(movie_id, page)
}
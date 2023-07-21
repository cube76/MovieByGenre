package com.example.moviebygenre.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    val genre = movieUseCase.getAllGenre().asLiveData()
    fun getMovies(genre: String, page: Int) =
        movieUseCase.getAllMovie(genre, page).asLiveData()
}
package com.example.moviebygenre.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    fun getDetailMovie(movie_id:Int, append_to_response:String) =
        movieUseCase.getDetailMovie(movie_id, append_to_response).asLiveData()

    fun getReview(movie_id:Int, page:Int) =
        movieUseCase.getReview(movie_id, page).asLiveData()
}
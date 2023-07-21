package com.example.core.data.source.remote.network

import com.example.core.domain.model.DetailMovie
import com.example.core.domain.model.GenreList
import com.example.core.domain.model.MovieList
import com.example.core.domain.model.Review
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getList(@Query("with_genres") with_genres: String?, @Query("page") page: Int): MovieList

    @GET("genre/movie/list")
    suspend fun getGenre(): GenreList

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id")movie_id: Int, @Query("append_to_response") append_to_response: String): DetailMovie

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewMovie(@Path("movie_id")movie_id: Int, @Query("page") page: Int): Review
}

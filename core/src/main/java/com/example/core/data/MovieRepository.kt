package com.example.core.data

import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.DetailResponse
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.ReviewResponse
import com.example.core.domain.model.*
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(genre: String, page:Int): Flow<Resource<MovieList>> =
        object : NetworkBoundResource<MovieList, MovieResponse>() {

            override suspend fun createCall(): Flow<ApiResponse<MovieList>> =
                remoteDataSource.getAllMovie(genre, page)

        }.asFlow()

    override fun getAllGenre(): Flow<Resource<GenreList>> =
        object : NetworkBoundResource<GenreList, GenreResponse>() {

            override suspend fun createCall(): Flow<ApiResponse<GenreList>> =
                remoteDataSource.getAllGenre()

        }.asFlow()

    override fun getDetailMovie(movie_id:Int, append_to_response:String): Flow<Resource<DetailMovie>> =
        object : NetworkBoundResource<DetailMovie, DetailResponse>() {

            override suspend fun createCall(): Flow<ApiResponse<DetailMovie>> =
                remoteDataSource.getDetailMovie(movie_id, append_to_response)

        }.asFlow()

    override fun getReview(movie_id:Int, page:Int): Flow<Resource<Review>> =
        object : NetworkBoundResource<Review, ReviewResponse>() {

            override suspend fun createCall(): Flow<ApiResponse<Review>> =
                remoteDataSource.getReview(movie_id, page)

        }.asFlow()

}


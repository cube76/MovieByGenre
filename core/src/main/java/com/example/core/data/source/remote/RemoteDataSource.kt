package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.DetailMovie
import com.example.core.domain.model.GenreList
import com.example.core.domain.model.MovieList
import com.example.core.domain.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMovie(genre: String, page:Int): Flow<ApiResponse<MovieList>> {
        //get data from remote api
        return flow {
            try {
                val dataArray = apiService.getList(genre, page)
                if (dataArray != null){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllGenre(): Flow<ApiResponse<GenreList>> {
        //get data from remote api
        return flow {
            try {
                val dataArray = apiService.getGenre()
                if (dataArray != null){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(movie_id:Int, append_to_response:String): Flow<ApiResponse<DetailMovie>> {
        //get data from remote api
        return flow {
            try {
                val dataArray = apiService.getDetailMovie(movie_id, append_to_response)
                if (dataArray != null){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReview(movie_id:Int, page:Int): Flow<ApiResponse<Review>> {
        //get data from remote api
        return flow {
            try {
                val dataArray = apiService.getReviewMovie(movie_id, page)
                if (dataArray != null){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}


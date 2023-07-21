package com.example.core.data

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType : Any> {

    private var result: Flow<Resource<ResultType>> = flow {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data))
                }
                is ApiResponse.Empty -> {
//                    emit(Resource.Empty(apiResponse.errorMessage))
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun createCall(): Flow<ApiResponse<ResultType>>


    fun asFlow(): Flow<Resource<ResultType>> = result
}
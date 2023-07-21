package com.example.core.di

import com.example.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class TokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer "+BuildConfig.accessToken)
            .build()
        return chain.proceed(newRequest)
    }
}
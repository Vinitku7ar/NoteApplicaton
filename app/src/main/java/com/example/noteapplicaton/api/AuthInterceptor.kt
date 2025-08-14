package com.example.noteapplicaton.api

import com.example.noteapplicaton.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    @Inject
    lateinit var tokkenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokkenManager.getToken()
        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}
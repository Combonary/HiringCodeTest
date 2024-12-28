package com.example.hiringcodetest.data.source.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val httpUrl = initialRequest.url.newBuilder()
            .build()

        val requestBuilder: Request.Builder = initialRequest.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}
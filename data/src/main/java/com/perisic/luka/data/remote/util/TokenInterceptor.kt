package com.perisic.luka.data.remote.util

import com.perisic.luka.data.local.dao.TokenDao
import com.perisic.luka.data.local.model.Token
import okhttp3.Interceptor
import okhttp3.Response

internal class TokenInterceptor(
    tokenDao: TokenDao
) : Interceptor {

    private var token: Token? = tokenDao.fetchTokenSync()

    init {
        tokenDao.fetchTokenAsync().observeForever {
            it?.let {
                token = it
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().apply {
            token?.accessToken?.let {
                addHeader("Authorization", "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}
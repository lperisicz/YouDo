package com.perisic.luka.data.remote.di

import com.perisic.luka.data.BuildConfig
import com.perisic.luka.data.remote.api.AuthService
import com.perisic.luka.data.remote.api.PostService
import com.perisic.luka.data.remote.api.TaxonomyService
import com.perisic.luka.data.remote.api.UserService
import com.perisic.luka.data.remote.util.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val RemoteModule = module {
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(TokenInterceptor(get()))
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            client(get())
        }.build()

    }
}

val ApiModule = module {

    single {
        get<Retrofit>().create(AuthService::class.java)
    }

    single {
        get<Retrofit>().create(PostService::class.java)
    }

    single {
        get<Retrofit>().create(UserService::class.java)
    }

    single {
        get<Retrofit>().create(TaxonomyService::class.java)
    }

}
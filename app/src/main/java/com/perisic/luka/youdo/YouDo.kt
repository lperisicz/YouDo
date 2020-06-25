package com.perisic.luka.youdo

import android.app.Application
import com.perisic.luka.auth.di.AuthModule
import com.perisic.luka.data.di.RepoModule
import com.perisic.luka.data.local.di.LocalModule
import com.perisic.luka.data.remote.di.ApiModule
import com.perisic.luka.data.remote.di.RemoteModule
import com.perisic.luka.post.di.PostModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YouDo : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YouDo)
            modules(
                listOf(
                    LocalModule,
                    RemoteModule,
                    ApiModule,
                    RepoModule,
                    AuthModule,
                    PostModule
                )
            )
        }
    }

}
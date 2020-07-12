package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.LocalDatabase
import com.perisic.luka.data.local.dao.TokenDao
import com.perisic.luka.data.local.model.Token
import com.perisic.luka.data.remote.api.AuthService
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.makeCall
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse

internal class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenDao: TokenDao,
    private val localDatabase: LocalDatabase
) : AuthRepository {

    override fun login(request: LoginRequest): LiveData<BaseResponse<LoginResponse>> {
        return authService.loginUser(request).makeCall { response ->
            response.data?.let {
                tokenDao.insert(
                    Token(
                        accessToken = it.token,
                        refreshToken = it.refreshToken
                    )
                )
            }
        }
    }

    override fun logout() {
        localDatabase.clearAllTables()
    }
}
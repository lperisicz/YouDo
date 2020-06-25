package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.remote.api.AuthService
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.makeCall
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse

internal class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {

    override fun login(request: LoginRequest): LiveData<BaseResponse<LoginResponse>> {
        return authService.loginUser(request).makeCall()
    }

}
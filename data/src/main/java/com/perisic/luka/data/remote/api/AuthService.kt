package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("Auth/login")
    fun loginUser(@Body loginRequestModel: LoginRequest): Call<BaseResponse<LoginResponse>>

}
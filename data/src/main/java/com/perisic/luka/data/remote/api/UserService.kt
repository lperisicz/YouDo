package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.request.UpdateUserRequest
import com.perisic.luka.data.remote.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserService {

    @GET("User/me")
    fun fetchUserData(): Call<BaseResponse<UserResponse>>

    @PATCH("User/me")
    fun updateUser(@Body updateUserRequest: UpdateUserRequest): Call<BaseResponse<UserResponse>>

}
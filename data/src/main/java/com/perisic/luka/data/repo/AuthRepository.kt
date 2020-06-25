package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.remote.model.response.LoginResponse

interface AuthRepository {

    fun login(request: LoginRequest): LiveData<BaseResponse<LoginResponse>>

}
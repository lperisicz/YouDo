package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.model.User
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.request.UpdateUserRequest
import com.perisic.luka.data.remote.model.response.UserResponse

interface UserRepository {

    fun fetchUserData()

    fun getUserData(): LiveData<User>

    fun updateUser(updateUserRequest: UpdateUserRequest): LiveData<BaseResponse<UserResponse>>

}
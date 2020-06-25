package com.perisic.luka.auth.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.repo.AuthRepository

class LoginViewModel(
    authRepository: AuthRepository
) : ViewModel() {

    private val loginRequest = MutableLiveData<LoginRequest>()
    val loginResponse = switchMap(loginRequest, authRepository::login)

    fun login(username: String, password: String) {
        loginRequest.value = LoginRequest(
            username, password
        )
    }

}
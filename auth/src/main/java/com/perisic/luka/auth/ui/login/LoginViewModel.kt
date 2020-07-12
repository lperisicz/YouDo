package com.perisic.luka.auth.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.NetworkState
import com.perisic.luka.data.remote.model.request.LoginRequest
import com.perisic.luka.data.repo.AuthRepository
import com.perisic.luka.data.repo.TaxonomyRepository
import com.perisic.luka.data.repo.UserRepository

internal class LoginViewModel(
    authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val taxonomyRepository: TaxonomyRepository
) : ViewModel() {

    private val loginRequest = MutableLiveData<LoginRequest>()
    val loginResponse = switchMap(loginRequest, authRepository::login)

    init {
        loginResponse.observeForever {
            if (it.status == BaseResponse.STATUS.SUCCESS) {
                userRepository.fetchUserData()
                taxonomyRepository.fetchTaxonomies()
            }
        }
    }

    fun login(username: String, password: String) {
        loginRequest.value = LoginRequest(
            username, password
        )
    }

}
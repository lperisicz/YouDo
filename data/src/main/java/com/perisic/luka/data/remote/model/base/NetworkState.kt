package com.perisic.luka.data.remote.model.base

data class NetworkState(
    val status: BaseResponse.STATUS,
    val message: String? = null
) {

    companion object {
        val SUCCESS = NetworkState(BaseResponse.STATUS.SUCCESS)
        val LOADING = NetworkState(BaseResponse.STATUS.LOADING)
        fun error(msg: String?) = NetworkState(BaseResponse.STATUS.ERROR, msg)
    }

}
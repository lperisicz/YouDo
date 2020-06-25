package com.perisic.luka.data.remote.model.base

data class BaseResponse<T> constructor(
    val data: T? = null,
    val message: String? = null,
    val code: String? = null,
    val status: STATUS = STATUS.LOADING
) {

    val networkState = NetworkState(
        status = status,
        message = message
    )

    constructor(baseResponse: BaseResponse<T>?, status: STATUS) : this(
        data = baseResponse?.data,
        message = baseResponse?.message,
        code = baseResponse?.code,
        status = status
    )

    enum class STATUS {
        LOADING, ERROR, SUCCESS
    }

    enum class ERROR {
        UNKNOWN
    }

}
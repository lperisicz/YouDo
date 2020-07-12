package com.perisic.luka.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.NetworkState
import com.perisic.luka.data.remote.model.base.parse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeyedSource<D, T>(
    private val apiSource: (D?) -> Call<BaseResponse<List<T>>>,
    private val getKeyFromData: (T?) -> D
) : ItemKeyedDataSource<D, T>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<D>, callback: LoadInitialCallback<T>) {
        apiSource(null).enqueueFilterSync(callback::onResult)
    }

    override fun loadAfter(params: LoadParams<D>, callback: LoadCallback<T>) {
        apiSource(params.key).enqueueFilterAsync(callback::onResult)
    }

    override fun loadBefore(params: LoadParams<D>, callback: LoadCallback<T>) {}

    override fun getKey(item: T) = getKeyFromData(item)

    private fun Call<BaseResponse<List<T>>>.enqueueFilterSync(callback: (List<T>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
        val response = execute()
        if (response.isSuccessful) {
            val body: BaseResponse<List<T>>? = response.body()
            body?.data?.let {
                callback(it)
                networkState.postValue(NetworkState.SUCCESS)
            } ?: let {
                networkState.postValue(NetworkState.error(BaseResponse.ERROR.UNKNOWN.name))
            }
        } else {
            val errorResponse: BaseResponse<List<T>>? = try {
                response.errorBody()?.charStream()?.parse()
            } catch (e: Exception) {
                null
            }
            errorResponse?.let {
                networkState.postValue(NetworkState.error(it.message))
            } ?: let {
                networkState.postValue(NetworkState.error(BaseResponse.ERROR.UNKNOWN.name))
            }
        }
    }

    private fun Call<BaseResponse<List<T>>>.enqueueFilterAsync(callback: (List<T>) -> Unit) {
        enqueue(
            object : Callback<BaseResponse<List<T>>> {

                override fun onFailure(call: Call<BaseResponse<List<T>>>, t: Throwable) {
                    networkState.postValue(NetworkState.error(t.message))
                }

                override fun onResponse(
                    call: Call<BaseResponse<List<T>>>,
                    response: Response<BaseResponse<List<T>>>
                ) {
                    if (response.isSuccessful) {
                        val body: BaseResponse<List<T>>? = response.body()
                        body?.data?.let {
                            callback(it)
                        } ?: let {
                            networkState.postValue(NetworkState.error(BaseResponse.ERROR.UNKNOWN.name))
                        }
                    } else {
                        val errorResponse: BaseResponse<List<T>>? = try {
                            response.errorBody()?.charStream()?.parse()
                        } catch (e: Exception) {
                            null
                        }
                        errorResponse?.let {
                            networkState.postValue(NetworkState.error(it.message))
                        } ?: let {
                            networkState.postValue(NetworkState.error(BaseResponse.ERROR.UNKNOWN.name))
                        }
                    }
                }
            }
        )
    }

}
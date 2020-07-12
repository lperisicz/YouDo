package com.perisic.luka.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.perisic.luka.data.remote.model.base.BaseResponse
import retrofit2.Call

class KeyedSourceFactory<D, T>(
    private val apiSource: (D?) -> Call<BaseResponse<List<T>>>,
    private val getKeyFromData: (T?) -> D
) : DataSource.Factory<D, T>() {

    private lateinit var latestSource: KeyedSource<D, T>
    internal val sourceLiveData = MutableLiveData<KeyedSource<D, T>>()

    override fun create(): DataSource<D, T> {
        latestSource = KeyedSource(
            apiSource = apiSource,
            getKeyFromData = getKeyFromData
        )
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}
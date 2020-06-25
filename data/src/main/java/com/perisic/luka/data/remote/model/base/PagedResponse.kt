package com.perisic.luka.data.remote.model.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PagedResponse<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>
)
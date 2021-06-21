package com.perisic.luka.post.ui.all

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.LocationRepository
import com.perisic.luka.data.repo.PostRepository

class AllPostsViewModel(
    postRepository: PostRepository,
    locationRepository: LocationRepository
) : ViewModel() {

    private val location = mapIf(
        locationRepository.getRecentLocation(),
        { it != null },
        { location -> Pair(location.latitude, location.longitude) }
    )
    private val pagedResponse = map(location) {
        postRepository.getAllPosts(it.first, it.second)
    }
    val postsResponse = switchMap(pagedResponse) { it.pagedList }
    val networkState = switchMap(pagedResponse) { it.networkState }

    init {
        location.observeForever {}
    }

    @MainThread
    private fun <X, Y> mapIf(
        source: LiveData<X>,
        predicate: (X?) -> Boolean,
        mapFunction: (X) -> Y
    ): LiveData<Y> {
        val result = MediatorLiveData<Y>()
        result.addSource(source) { x ->
            if (predicate(x)) {
                result.value = mapFunction(x)
            }
        }
        return result
    }

}
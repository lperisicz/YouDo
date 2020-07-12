package com.perisic.luka.post.ui.all

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.LocationRepository
import com.perisic.luka.data.repo.PostRepository

class AllPostsViewModel(
    postRepository: PostRepository,
    locationRepository: LocationRepository
) : ViewModel() {

    private val location = map(locationRepository.getRecentLocation()) {
        Pair(it.latitude, it.longitude)
    }
    private val pagedResponse = map(location) {
        postRepository.getAllPosts(it.first, it.second)
    }
    val postsResponse = switchMap(pagedResponse) { it.pagedList }
    val networkState = switchMap(pagedResponse) { it.networkState }

    init {
        location.observeForever {}
    }

}
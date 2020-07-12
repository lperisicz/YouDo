package com.perisic.luka.post.ui.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.data.remote.model.request.CreatePostRequest
import com.perisic.luka.data.repo.LocationRepository
import com.perisic.luka.data.repo.PostRepository

internal class CreatePostViewModel(
    postRepository: PostRepository,
    locationRepository: LocationRepository
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val full = MutableLiveData<Boolean>()
    private val location = map(locationRepository.getRecentLocation()) {
        Pair(it.latitude, it.longitude)
    }
    val useLocation = MutableLiveData(false)

    private val createRequest = MutableLiveData<CreatePostRequest>()

    val createPostResponse = switchMap(createRequest, postRepository::createPost)

    init {
        location.observeForever { }
    }

    fun createPost() {
        createRequest.value = CreatePostRequest(
            title = title.value,
            description = description.value,
            priceAmount = price.value?.toDoubleOrNull(),
            priceMode = if (full.value == true) "full" else "perHour",
            latitude = location.value?.first,
            longitude = location.value?.second,
            noLocation = useLocation.value == false
        )
    }

}
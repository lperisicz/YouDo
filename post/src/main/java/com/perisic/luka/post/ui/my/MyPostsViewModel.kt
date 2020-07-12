package com.perisic.luka.post.ui.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.base.extensions.mapMutable
import com.perisic.luka.data.repo.PostRepository
import com.perisic.luka.data.repo.UserRepository

class MyPostsViewModel(
    postRepository: PostRepository,
    userRepository: UserRepository
) : ViewModel() {

    private val userDataResponse = userRepository.getUserData()
    private val userId = mapMutable(userDataResponse) { it?.id }
    private val pagedResponse = map(userId) {
        it?.let { id -> if (id != 0) postRepository.getMyPosts(id) else null }
    }
    val postsResponse = switchMap(pagedResponse) { it?.pagedList }
    val networkState = switchMap(pagedResponse) { it?.networkState }
    private val postIdToDelete = MutableLiveData<Int>()
    val postDeleteResponse = switchMap(postIdToDelete, postRepository::deletePost)

    fun deletePost(postId: Int) {
        postIdToDelete.value = postId
    }

}
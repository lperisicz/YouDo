package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.PagedResponse
import com.perisic.luka.data.remote.model.request.CreatePostRequest
import com.perisic.luka.data.remote.model.response.PostResponse

interface PostRepository {

    fun getMyPosts(userId: Int): PagedResponse<PostResponse>

    fun getAllPosts(latitude: Double, longitude: Double): PagedResponse<PostResponse>

    fun deletePost(postId: Int): LiveData<BaseResponse<Any>>

    fun createPost(createPostRequest: CreatePostRequest): LiveData<BaseResponse<Any>>

}
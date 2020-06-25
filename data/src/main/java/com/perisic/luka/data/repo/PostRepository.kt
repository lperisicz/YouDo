package com.perisic.luka.data.repo

import com.perisic.luka.data.remote.model.base.PagedResponse
import com.perisic.luka.data.remote.model.response.PostResponse

interface PostRepository {

    fun getMyPosts(userId: Int): PagedResponse<PostResponse>

    fun getAllPosts(): PagedResponse<PostResponse>

}
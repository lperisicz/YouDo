package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.toLiveData
import com.perisic.luka.data.remote.api.PostService
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.PagedResponse
import com.perisic.luka.data.remote.model.base.makeCall
import com.perisic.luka.data.remote.model.request.CreatePostRequest
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.data.source.KeyedSourceFactory
import java.util.concurrent.Executors

internal class PostRepositoryImpl(
    private val postService: PostService
) : PostRepository {

    private val networkExecutor = Executors.newFixedThreadPool(5)
    private var myPostsResponse: PagedResponse<PostResponse>? = null
    private var allPostsResponse: PagedResponse<PostResponse>? = null

    override fun getMyPosts(userId: Int): PagedResponse<PostResponse> {
        myPostsResponse?.let {
            return it
        }
        val sourceFactory = KeyedSourceFactory<Int, PostResponse>(
            apiSource = {
                postService.fetchMyPosts(afterId = it ?: 0, userId = userId)
            }
        ) {
            it?.id ?: 0
        }
        val pagedList = sourceFactory.toLiveData(
            pageSize = 20,
            fetchExecutor = networkExecutor,
            initialLoadKey = 0
        )
        myPostsResponse = PagedResponse(
            pagedList = pagedList,
            networkState = switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            }
        )
        return myPostsResponse!!
    }

    override fun getAllPosts(latitude: Double, longitude: Double): PagedResponse<PostResponse> {
        allPostsResponse?.let {
            return it
        }
        val sourceFactory = KeyedSourceFactory<Int, PostResponse>(
            apiSource = {
                postService.fetchAllPosts(
                    afterId = it ?: 0,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        ) {
            it?.id ?: 0
        }
        val pagedList = sourceFactory.toLiveData(
            pageSize = 20,
            fetchExecutor = networkExecutor,
            initialLoadKey = 0
        )
        allPostsResponse = PagedResponse(
            pagedList = pagedList,
            networkState = switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            }
        )
        return allPostsResponse!!
    }

    override fun deletePost(postId: Int): LiveData<BaseResponse<Any>> {
        return postService.deletePost(postId).makeCall()
    }

    override fun createPost(createPostRequest: CreatePostRequest): LiveData<BaseResponse<Any>> {
        return postService.createPost(createPostRequest).makeCall()
    }
}
package com.perisic.luka.data.repo

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.toLiveData
import com.perisic.luka.data.remote.api.PostService
import com.perisic.luka.data.remote.model.base.PagedResponse
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.data.source.KeyedSourceFactory
import java.util.concurrent.Executors

internal class PostRepositoryImpl(
    private val postService: PostService
) : PostRepository {

    private val networkExecutor = Executors.newFixedThreadPool(5)

    override fun getMyPosts(userId: Int): PagedResponse<PostResponse> {
        val sourceFactory = KeyedSourceFactory<Int, PostResponse>(
            apiSource = {
                postService.fetchMyPosts(afterId = it ?: 0, userId = userId)
            }
        ) {
            it.id
        }
        val pagedList = sourceFactory.toLiveData(
            pageSize = 20,
            fetchExecutor = networkExecutor
        )
        return PagedResponse(
            pagedList = pagedList,
            networkState = switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            }
        )
    }

    override fun getAllPosts(): PagedResponse<PostResponse> {
        val sourceFactory = KeyedSourceFactory<Int, PostResponse>(
            apiSource = {
                postService.fetchAllPosts(afterId = it ?: 0)
            }
        ) {
            it.id
        }
        val pagedList = sourceFactory.toLiveData(
            pageSize = 20,
            fetchExecutor = networkExecutor
        )
        return PagedResponse(
            pagedList = pagedList,
            networkState = switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            }
        )
    }
}
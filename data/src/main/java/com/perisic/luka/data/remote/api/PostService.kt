package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.response.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("Post/showAll")
    fun fetchMyPosts(
        @Query("afterId") afterId: Int,
        @Query("beforeId") beforeId: Int = 0,
        @Query("userId") userId: Int
    ): Call<BaseResponse<List<PostResponse>>>

    @GET("Post/showAll")
    fun fetchAllPosts(
        @Query("afterId") afterId: Int,
        @Query("beforeId") beforeId: Int = 0,
        @Query("userId") userId: Int = 0
    ): Call<BaseResponse<List<PostResponse>>>

}
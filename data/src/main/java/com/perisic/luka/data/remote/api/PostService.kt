package com.perisic.luka.data.remote.api

import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.request.CreatePostRequest
import com.perisic.luka.data.remote.model.response.PostResponse
import retrofit2.Call
import retrofit2.http.*

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
        @Query("userId") userId: Int = 0,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Call<BaseResponse<List<PostResponse>>>

    @DELETE("Post/{id}")
    fun deletePost(@Path("id") postId: Int): Call<BaseResponse<Any>>

    @POST("Post")
    fun createPost(@Body createPostRequest: CreatePostRequest): Call<BaseResponse<Any>>

}
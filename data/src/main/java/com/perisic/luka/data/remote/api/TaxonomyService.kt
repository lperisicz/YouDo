package com.perisic.luka.data.remote.api

import com.perisic.luka.data.local.model.Taxonomy
import com.perisic.luka.data.remote.model.base.BaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface TaxonomyService {

    @GET("Taxonomy")
    fun fetchTaxonomies(): Call<BaseResponse<List<Taxonomy>>>

}
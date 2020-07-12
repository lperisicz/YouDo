package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.model.Taxonomy

interface TaxonomyRepository {

    fun fetchTaxonomies()

    fun getTaxonomies(): LiveData<List<Taxonomy>>

}
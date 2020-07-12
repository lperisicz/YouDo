package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.dao.TaxonomyDao
import com.perisic.luka.data.local.model.Taxonomy
import com.perisic.luka.data.remote.api.TaxonomyService
import com.perisic.luka.data.remote.model.base.makeCall

internal class TaxonomyRepositoryImpl(
    private val taxonomyService: TaxonomyService,
    private val taxonomyDao: TaxonomyDao
) : TaxonomyRepository {

    override fun fetchTaxonomies() {
        taxonomyService.fetchTaxonomies().makeCall { response ->
            response.data?.let {
                taxonomyDao.insertIgnoreConflict(it)
            }
        }
    }

    override fun getTaxonomies(): LiveData<List<Taxonomy>> = taxonomyDao.getAll()
}
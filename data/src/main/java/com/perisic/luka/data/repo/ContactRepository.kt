package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.model.Contact

interface ContactRepository {

    fun getContacts(): LiveData<List<Contact>>

    fun updateContact(type: String, value: String?)

}
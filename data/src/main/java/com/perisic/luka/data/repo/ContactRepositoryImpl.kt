package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.dao.ContactDao
import com.perisic.luka.data.local.model.Contact

internal class ContactRepositoryImpl(
    private val contactDao: ContactDao
) : ContactRepository {

    override fun getContacts(): LiveData<List<Contact>> = contactDao.getAll()

    override fun updateContact(type: String, value: String?) {
        contactDao.update(
            Contact(
                type = type,
                value = value ?: ""
            )
        )
    }
}
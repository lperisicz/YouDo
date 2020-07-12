package com.perisic.luka.user.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.perisic.luka.base.extensions.mapMutable
import com.perisic.luka.data.local.model.Contact
import com.perisic.luka.data.remote.model.request.UpdateUserRequest
import com.perisic.luka.data.repo.ContactRepository
import com.perisic.luka.data.repo.TaxonomyRepository
import com.perisic.luka.data.repo.UserRepository

internal class UserViewModel(
    userRepository: UserRepository,
    contactRepository: ContactRepository,
    taxonomyRepository: TaxonomyRepository
) : ViewModel() {

    private val request = MutableLiveData(null)
    private val updateUserRequest = MutableLiveData<UpdateUserRequest>()

    private val userResponse = switchMap(request) { userRepository.getUserData() }
    private val contactsResponse = switchMap(request) { contactRepository.getContacts() }
    private val taxonomiesResponse = switchMap(request) { taxonomyRepository.getTaxonomies() }

    val updateUserResponse = switchMap(updateUserRequest, userRepository::updateUser)

    val phone = mapMutable(contactsResponse) { contacts ->
        contacts.find { it.type == "phone" }?.value
    }
    val email = mapMutable(contactsResponse) { contacts ->
        contacts.find { it.type == "email" }?.value
    }
    val radius = mapMutable(userResponse) { it.radius }
    val taxonomies = mapMutable(taxonomiesResponse) { it }

    fun updateUserData() {
        val request = UpdateUserRequest(
            taxonomyIds = taxonomies.value?.filter { it.selected }?.map { it.id } ?: listOf(),
            contacts = listOf(
                Contact(
                    type = "phone",
                    value = phone.value ?: ""
                ),
                Contact(
                    type = "email",
                    value = email.value ?: ""
                )
            ),
            radius = radius.value ?: 0
        )
        updateUserRequest.value = request
    }

}
package com.perisic.luka.data.repo

import androidx.lifecycle.LiveData
import com.perisic.luka.data.local.dao.ContactDao
import com.perisic.luka.data.local.dao.TaxonomyDao
import com.perisic.luka.data.local.dao.UserDao
import com.perisic.luka.data.local.model.Taxonomy
import com.perisic.luka.data.local.model.User
import com.perisic.luka.data.remote.api.UserService
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.data.remote.model.base.makeCall
import com.perisic.luka.data.remote.model.request.UpdateUserRequest
import com.perisic.luka.data.remote.model.response.UserResponse

internal class UserRepositoryImpl(
    private val userService: UserService,
    private val userDao: UserDao,
    private val contactDao: ContactDao,
    private val taxonomyDao: TaxonomyDao
) : UserRepository {

    override fun fetchUserData() {
        userService.fetchUserData().makeCall { response ->
            response.data?.let {
                contactDao.insert(it.contacts)
                userDao.insert(
                    User(
                        id = it.id,
                        username = it.username,
                        email = it.email,
                        radius = it.radius
                    )
                )
                taxonomyDao.insert(
                    it.taxonomies.map { taxonomy ->
                        Taxonomy(
                            id = taxonomy.id,
                            title = taxonomy.title,
                            type = taxonomy.type,
                            selected = true
                        )
                    }
                )
            }
        }
    }

    override fun getUserData(): LiveData<User> = userDao.getUserData()

    override fun updateUser(updateUserRequest: UpdateUserRequest): LiveData<BaseResponse<UserResponse>> {
        return userService.updateUser(updateUserRequest).makeCall { response ->
            response.data?.let {
                taxonomyDao.updateSelected(it.taxonomies.map { taxonomy ->
                    taxonomy.selected = true
                    taxonomy
                })
                contactDao.insert(it.contacts)
                userDao.insert(
                    User(
                        id = it.id,
                        username = it.username,
                        email = it.email,
                        radius = it.radius
                    )
                )
            }
        }
    }
}
package com.perisic.luka.data.remote.model.request

import com.perisic.luka.data.local.model.Contact

data class UpdateUserRequest(
    val taxonomyIds: List<Int>,
    val radius: Int,
    val contacts: List<Contact>
)
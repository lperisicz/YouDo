package com.perisic.luka.data.remote.model.response

import com.perisic.luka.data.local.model.Contact
import com.perisic.luka.data.local.model.Taxonomy

class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    val radius: Int,
    val contacts: List<Contact>,
    val taxonomies: List<Taxonomy>
)
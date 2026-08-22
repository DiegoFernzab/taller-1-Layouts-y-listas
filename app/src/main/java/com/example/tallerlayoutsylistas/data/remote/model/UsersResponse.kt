package com.example.tallerlayoutsylistas.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<User>,
    val total: Int
)

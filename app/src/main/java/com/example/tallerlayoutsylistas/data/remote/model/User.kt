package com.example.tallerlayoutsylistas.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val phone: String,
    val company: Company,
    val gender: String,
    val email: String,
    val birthDate: String,
    val eyeColor: String,
    val university: String,
    val role: String
)

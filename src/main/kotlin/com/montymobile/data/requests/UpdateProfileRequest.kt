package com.montymobile.data.requests

data class UpdateProfileRequest(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val instagramUrl: String,
    val linkedinUrl: String,
    val skills: List<String>,
    val profileImageChanged: Boolean = false
)

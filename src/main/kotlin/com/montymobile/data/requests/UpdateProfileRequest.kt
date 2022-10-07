package com.montymobile.data.requests

import com.montymobile.data.responses.SkillDto

data class UpdateProfileRequest(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val instagramUrl: String,
    val linkedinUrl: String,
    val skills: List<SkillDto>,
    val profileImageChanged: Boolean = false
)

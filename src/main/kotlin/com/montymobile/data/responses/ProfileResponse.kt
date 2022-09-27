package com.montymobile.data.responses

data class ProfileResponse(
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val profilePictureUrl: String,
    val topSkillUrls: List<String>,
    val gitHubUrl: String?,
    val instagramUrl: String?,
    val linkedinUrl: String?,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
)

package com.montymobile.data.requests

data class FollowUpdateRequest(
    val followingUserId: String,
    val followedUserId: String
)
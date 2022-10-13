package com.montymobile.data.responses

data class PostResponse(
    val id: String,
    val userId: String,
    val username: String,
    val imageUrl: String? = null,
    val profilePictureUrl: String? = null,
    val description: String? = null,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean
)

package com.montymobile.data.responses

data class CommentResponse(
    val id: String,
    val username: String,
    val profileImageUrl: String,
    val timeStamp: Long,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int
)

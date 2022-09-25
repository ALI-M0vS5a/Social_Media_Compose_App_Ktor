package com.montymobile.service

import com.montymobile.data.models.Post
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.requests.CreatePostRequest

class PostService(
    private val postRepository: PostRepository
) {

    suspend fun createPostIfUserExists(request: CreatePostRequest): Boolean {
        return  postRepository.createPostIfUserExists(
            Post(
                imageUrl = "",
                userId = request.userId,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }
}
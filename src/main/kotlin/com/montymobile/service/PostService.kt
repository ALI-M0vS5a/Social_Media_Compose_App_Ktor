package com.montymobile.service

import com.montymobile.data.models.Post
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.requests.CreatePostRequest
import com.montymobile.util.Constants

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

    suspend fun getPostsForFollows(
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_POST_PAGE_SIZE
    ): List<Post> {
        return postRepository.getPostByFollows(userId, page, pageSize)
    }
}
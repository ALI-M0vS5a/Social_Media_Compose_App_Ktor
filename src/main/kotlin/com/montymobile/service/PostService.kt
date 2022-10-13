package com.montymobile.service

import com.montymobile.data.models.Post
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.requests.CreatePostRequest
import com.montymobile.data.responses.PostResponse
import com.montymobile.util.Constants

class PostService(
    private val postRepository: PostRepository
) {

    suspend fun createPost(
        request: CreatePostRequest,
        userId: String,
        imageUrl: String,
    ): Boolean {
        return postRepository.createPost(
            Post(
                imageUrl = imageUrl,
                userId = userId,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }

    suspend fun getPostsForFollows(
        ownUserId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<PostResponse> {
        return postRepository.getPostByFollows(ownUserId, page, pageSize)
    }

    suspend fun getPostsForProfile(
        ownUserId: String,
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<PostResponse> {
        return postRepository.getPostForProfile(ownUserId,userId, page, pageSize)
    }


    suspend fun getPost(
        postId: String
    ): Post? {
        return postRepository.getPost(postId)
    }

    suspend fun getPostDetails(ownUserId: String, postId: String): PostResponse? {
        return postRepository.getPostDetails(ownUserId,postId)
    }

    suspend fun deletePost(postId: String) {
        postRepository.deletePost(postId)
    }

}
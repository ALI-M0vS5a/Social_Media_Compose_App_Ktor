package com.montymobile.service

import com.montymobile.data.models.Post
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.requests.CreatePostRequest
import com.montymobile.util.Constants

class PostService(
    private val postRepository: PostRepository
) {

    suspend fun createPostIfUserExists(request: CreatePostRequest, userId: String): Boolean {
        return  postRepository.createPostIfUserExists(
            Post(
                imageUrl = "",
                userId = userId,
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


    suspend fun getPost(postId: String): Post? = postRepository.getPost(postId)

    suspend fun deletePost(postId: String) {
        postRepository.deletePost(postId)
    }

}
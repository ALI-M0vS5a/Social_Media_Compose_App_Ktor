package com.montymobile.service

import com.montymobile.data.models.Comment
import com.montymobile.data.repository.comment.CommentRepository
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.data.requests.CreateCommentRequest
import com.montymobile.data.responses.CommentResponse
import com.montymobile.util.Constants

class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) {
   suspend fun createComment(createCommentRequest: CreateCommentRequest, userId: String): ValidationEvents {
       createCommentRequest.apply {
           if(comment.isBlank() || postId.isBlank()){
               return ValidationEvents.ErrorFieldEmpty
           }
           if (comment.length > Constants.MAX_COMMENT_LENGTH){
               return ValidationEvents.ErrorCommentTooLong
           }
       }
       val user = userRepository.getUserById(userId) ?: return ValidationEvents.UserNotFound
       commentRepository.createComment(
           Comment(
               username = user.username,
               profileImageUrl = user.profileImageUrl,
               likeCount = 0,
               comment = createCommentRequest.comment,
               userId = userId,
               postId = createCommentRequest.postId,
               timestamp = System.currentTimeMillis()
           )
       )
       return ValidationEvents.Success
   }
    suspend fun deleteComment(commentId: String): Boolean {
        return commentRepository.deleteComment(commentId)
    }
    suspend fun getCommentsForPost(postId: String, ownUserId: String): List<CommentResponse> {
        return commentRepository.getCommentsForPost(postId, ownUserId)
    }

    suspend fun getCommentById(commentId: String): Comment? {
        return commentRepository.getComment(commentId)
    }

    suspend fun deleteCommentForPost(postId: String) {
       commentRepository.deleteCommentsFromPost(postId)
    }

    sealed class ValidationEvents {
        object ErrorFieldEmpty: ValidationEvents()
        object ErrorCommentTooLong: ValidationEvents()
        object Success: ValidationEvents()
        object UserNotFound: ValidationEvents()
    }
}


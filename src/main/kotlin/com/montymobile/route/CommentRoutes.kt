package com.montymobile.route

import com.montymobile.data.requests.CreateCommentRequest
import com.montymobile.data.requests.DeleteCommentRequest
import com.montymobile.data.requests.LikeUpdateRequest
import com.montymobile.data.responses.BasicApiResponse
import com.montymobile.service.CommentService
import com.montymobile.service.LikeService
import com.montymobile.service.UserService
import com.montymobile.util.ApiResponseMessages
import com.montymobile.util.QueryParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.createComment(
    commentService: CommentService,
    userService: UserService
){
    authenticate {
        post("/api/comment/create"){
            val request = kotlin.runCatching { call.receiveNullable<CreateCommentRequest>() }.getOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            ifEmailBelongsToUser(
                userId = request.userId,
                validateEmail = userService::doesEmailBelongToUserId
            ){
                when(commentService.createComment(request)) {
                    is CommentService.ValidationEvents.ErrorFieldEmpty -> {
                        call.respond(
                            HttpStatusCode.OK,
                            BasicApiResponse(
                                successful = false,
                                message = ApiResponseMessages.FIELDS_BLANK
                            )
                        )
                    }
                    is CommentService.ValidationEvents.ErrorCommentTooLong -> {
                        call.respond(
                            HttpStatusCode.OK,
                            BasicApiResponse(
                                successful = false,
                                message = ApiResponseMessages.COMMENT_TOO_LONG
                            )
                        )
                    }
                    is CommentService.ValidationEvents.Success -> {
                        call.respond(
                            HttpStatusCode.OK,
                            BasicApiResponse(
                                successful = true
                            )
                        )
                    }
                }
            }
        }
    }
}

fun Route.getCommentsForPost(
    commentService: CommentService
){
    authenticate {
        get("/api/comment/get") {
            val postId = call.parameters[QueryParams.PARAM_POST_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val comments = commentService.getCommentsForPost(postId)
            call.respond(HttpStatusCode.OK, comments)
        }
    }
}

fun Route.deleteComment(
    commentService: CommentService,
    userService: UserService,
    likeService: LikeService
){
    authenticate {
        delete("/api/comment/delete") {
            val request = kotlin.runCatching { call.receiveNullable<DeleteCommentRequest>() }.getOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            ifEmailBelongsToUser(
                userId = request.userId,
                validateEmail = userService::doesEmailBelongToUserId
            ){
                val deleted = commentService.deleteComment(request.commentId)
                if(deleted) {
                    likeService.deleteLikesForParent(request.commentId)
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = true
                        )
                    )
                } else {
                    call.respond(HttpStatusCode.OK,BasicApiResponse(successful = false))
                }
            }
        }
    }
}
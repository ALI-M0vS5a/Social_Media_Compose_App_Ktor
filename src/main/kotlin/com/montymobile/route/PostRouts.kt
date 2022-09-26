package com.montymobile.route

import com.montymobile.data.requests.CreatePostRequest
import com.montymobile.data.requests.DeletePostRequest
import com.montymobile.data.responses.BasicApiResponse
import com.montymobile.plugins.email
import com.montymobile.service.LikeService
import com.montymobile.service.PostService
import com.montymobile.service.UserService
import com.montymobile.util.ApiResponseMessages.USER_NOT_FOUND
import com.montymobile.util.Constants
import com.montymobile.util.QueryParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPost(
    postService: PostService,
    userService: UserService
) {
    authenticate {
        post("/api/post/create") {
            val request =
                kotlin.runCatching { call.receiveNullable<CreatePostRequest>() }.getOrNull() ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

            ifEmailBelongsToUser(
                userId = request.userId,
                validateEmail = userService::doesEmailBelongToUserId
            ){
                val didUserExists = postService.createPostIfUserExists(request)
                if (!didUserExists) {
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(
                            successful = false,
                            message = USER_NOT_FOUND
                        )
                    )
                } else {
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

fun Route.getPostForFollows(
    postService: PostService,
    userService: UserService

) {
    authenticate {
        get("/api/post/get") {
            val userId = call.parameters[QueryParams.PARAM_USER_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: Constants.DEFAULT_POST_PAGE_SIZE

            val isEmailByUser = userService.doesEmailBelongToUserId(
                email = call.principal<JWTPrincipal>()?.email ?: "",
                userId = userId
            )

            if(!isEmailByUser){

            }

            ifEmailBelongsToUser(
                userId = userId,
                validateEmail = userService::doesEmailBelongToUserId
            ) {
                val posts = postService.getPostsForFollows(
                    userId = userId,
                    page = page,
                    pageSize = pageSize
                )
                call.respond(
                    HttpStatusCode.OK,
                    posts
                )
            }
        }
    }
}

fun Route.deletePost(
    postService: PostService,
    userService: UserService,
    likeService: LikeService
){
    delete("/api/post/delete") {
        val request =
            kotlin.runCatching { call.receiveNullable<DeletePostRequest>() }.getOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

        val post = postService.getPost(request.postId)
        if(post == null) {
            call.respond(HttpStatusCode.NotFound)
            return@delete
        }
        ifEmailBelongsToUser(
            userId = post.userId,
            validateEmail = userService::doesEmailBelongToUserId
        ){
            postService.deletePost(request.postId)
            likeService.deleteLikesForParent(request.postId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
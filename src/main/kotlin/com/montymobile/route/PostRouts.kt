package com.montymobile.route

import com.montymobile.data.models.Post
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.requests.CreatePostRequest
import com.montymobile.data.responses.BasicApiResponse
import com.montymobile.util.ApiResponseMessages
import com.montymobile.util.ApiResponseMessages.USER_NOT_FOUND
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createPostRoute(postRepository: PostRepository){
    post("/api/post/create") {
        val request =
            kotlin.runCatching { call.receiveNullable<CreatePostRequest>() }.getOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

        val didUserExists = postRepository.createPostIfUserExists(
            Post(
                imageUrl = "",
                userId = request.userId,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
        if(!didUserExists){
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
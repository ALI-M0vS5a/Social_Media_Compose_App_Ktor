package com.montymobile.plugins

import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.route.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    val followRepository: FollowRepository by inject()
    val postRepository: PostRepository by inject()
    routing {
        // User routes
        createUserRoute(userRepository)
        loginUser(userRepository)
        // Following routes
        followUser(followRepository)
        unfollowUser(followRepository)

        // Post routes
        createPostRoute(postRepository)

    }
}

package com.montymobile.plugins

import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.route.*
import com.montymobile.service.FollowService
import com.montymobile.service.PostService
import com.montymobile.service.UserService
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userService: UserService by inject()
    val followService: FollowService by inject()
    val postService: PostService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    routing {
        // User routes
        createUserRoute(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
        )
        // Following routes
        followUser(followService)
        unfollowUser(followService)

        // Post routes
        createPostRoute(
            postService = postService,
            userService = userService
        )

    }
}

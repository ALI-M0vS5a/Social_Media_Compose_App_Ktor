package com.montymobile.plugins

import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.route.createUserRoute
import com.montymobile.route.followUser
import com.montymobile.route.loginUser
import com.montymobile.route.unfollowUser
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    val followRepository: FollowRepository by inject()
    routing {
        // User routes
        createUserRoute(userRepository)
        loginUser(userRepository)
        // Following routes
        followUser(followRepository)
        unfollowUser(followRepository)

    }
}

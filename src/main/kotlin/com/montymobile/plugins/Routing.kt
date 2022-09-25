package com.montymobile.plugins

import com.montymobile.data.repository.user.UserRepository
import com.montymobile.route.createUserRoute
import com.montymobile.route.loginUser
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    routing {
        createUserRoute(
            userRepository = userRepository
        )
        loginUser(
            userRepository = userRepository
        )
    }
}

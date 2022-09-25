package com.montymobile.plugins

import com.montymobile.repository.user.UserRepository
import com.montymobile.route.createUserRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository: UserRepository by inject()
    routing {
        createUserRoute(
            userRepository = userRepository
        )
    }
}

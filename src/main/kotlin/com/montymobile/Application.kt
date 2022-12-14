package com.montymobile

import com.montymobile.di.mainModule
import io.ktor.server.application.*
import com.montymobile.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused")
fun Application.module() {
        install(Koin){
            modules(mainModule)
        }
        configureSerialization()
        configureMonitoring()
        configureHTTP()
        configureSecurity()
        configureSockets()
        configureSessions()
        configureRouting()

}

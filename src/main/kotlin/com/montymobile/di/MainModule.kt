package com.montymobile.di

import com.montymobile.controller.user.UserController
import com.montymobile.controller.user.UserControllerImpl
import com.montymobile.util.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserController>{
        UserControllerImpl(get())
    }
}
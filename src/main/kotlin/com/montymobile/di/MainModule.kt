package com.montymobile.di

import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.follow.FollowRepositoryImpl
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.repository.post.PostRepositoryImpl
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.data.repository.user.UserRepositoryImpl
import com.montymobile.service.FollowService
import com.montymobile.service.PostService
import com.montymobile.service.UserService
import com.montymobile.util.Constants
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DATABASE_NAME)
    }
    single<UserRepository>{
        UserRepositoryImpl(get())
    }
    single<FollowRepository>{
        FollowRepositoryImpl(get())
    }
    single<PostRepository>{
        PostRepositoryImpl(get())
    }
    single { UserService(get()) }
    single { FollowService(get()) }
    single { PostService(get()) }
}
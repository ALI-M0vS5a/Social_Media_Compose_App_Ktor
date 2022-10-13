package com.montymobile.di

import com.google.gson.Gson
import com.montymobile.data.repository.activity.ActivityRepository
import com.montymobile.data.repository.activity.ActivityRepositoryImpl
import com.montymobile.data.repository.chat.ChatRepository
import com.montymobile.data.repository.chat.ChatRepositoryImpl
import com.montymobile.data.repository.comment.CommentRepository
import com.montymobile.data.repository.comment.CommentRepositoryImpl
import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.follow.FollowRepositoryImpl
import com.montymobile.data.repository.likes.LikeRepository
import com.montymobile.data.repository.likes.LikeRepositoryImpl
import com.montymobile.data.repository.post.PostRepository
import com.montymobile.data.repository.post.PostRepositoryImpl
import com.montymobile.data.repository.skill.SkillRepository
import com.montymobile.data.repository.skill.SkillRepositoryImpl
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.data.repository.user.UserRepositoryImpl
import com.montymobile.service.*
import com.montymobile.service.chat.ChatController
import com.montymobile.service.chat.ChatService
import com.montymobile.util.Constants
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
    single<LikeRepository>{
        LikeRepositoryImpl(get())
    }
    single<CommentRepository>{
        CommentRepositoryImpl(get())
    }
    single<ActivityRepository>{
        ActivityRepositoryImpl(get())
    }
    single<SkillRepository>{
        SkillRepositoryImpl(get())
    }

    single<ChatRepository>{
        ChatRepositoryImpl(get())
    }

    single { UserService(get(), get()) }
    single { FollowService(get()) }
    single { PostService(get()) }
    single { LikeService(get(), get(), get()) }
    single { CommentService(get(), get()) }
    single { ActivityService(get(), get(), get()) }
    single { SkillService(get()) }
    single { ChatService(get()) }

    single { Gson() }

    single { ChatController(get()) }
}
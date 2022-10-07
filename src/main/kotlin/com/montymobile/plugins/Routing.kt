package com.montymobile.plugins

import com.montymobile.route.*
import com.montymobile.service.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userService: UserService by inject()
    val followService: FollowService by inject()
    val postService: PostService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()
    val activityService: ActivityService by inject()
    val skillService: SkillService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    routing {
        // User routes
        authenticate()
        createUser(userService)
        loginUser(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
        )
        searchUser(userService)
        getUserprofile(userService)
        getPostsForProfile(postService)
        updateUserProfile(userService)
        // Following routes
        followUser(followService,activityService)
        unfollowUser(followService)

        // Post routes
        createPost(postService)
        getPostForFollows(postService)
        deletePost(postService,likeService,commentService)
        // Like routes
        likeParent(likeService,activityService)
        unlikeParent(likeService)
        getLikedForParent(likeService)
        // Comment routes
        createComment(commentService,activityService)
        deleteComment(commentService,likeService)
        getCommentsForPost(commentService)
        //Activity routes
        getActivities(activityService)

        //Skill Routes
        getSkills(skillService)

        static {
            resources("static")
        }
    }
}

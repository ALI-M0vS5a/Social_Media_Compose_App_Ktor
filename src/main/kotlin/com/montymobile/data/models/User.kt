package com.montymobile.data.models

import com.montymobile.data.responses.SkillDto
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class User(
    val email: String,
    val username: String,
    val password: String,
    val bannerUrl: String?,
    val profileImageUrl: String,
    val bio: String,
    val skills: List<SkillDto> = listOf(),
    val gitHubUrl: String?,
    val instagramUrl: String?,
    val linkedInUrl: String?,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val postCount: Int = 0,
    @BsonId
    val id: String = ObjectId().toString(),
)

package com.montymobile.data.repository.user

import com.montymobile.data.models.User
import com.montymobile.data.requests.UpdateProfileRequest

interface UserRepository {

    suspend fun createUser(user: User)

    suspend fun getUserById(id: String): User?

    suspend fun getUserByEmail(email : String): User?

    suspend fun doesPasswordForUserMatch(email: String, enteredPassword: String): Boolean

    suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean

    suspend fun searchForUsers(query: String): List<User>

    suspend fun updateUser(
        userId: String,
        bannerUrl: String?,
        profileImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean

    suspend fun getUsers(userIds: List<String>): List<User>
}
package com.montymobile.repository.user

import com.montymobile.data.models.User
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.data.requests.UpdateProfileRequest

class FakeUserRepository : UserRepository {
    val users = mutableListOf<User>()

    override suspend fun createUser(user: User) {
        users.add(user)
    }

    override suspend fun getUserById(id: String): User? {
        return users.find { it.id == id }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    override suspend fun doesPasswordForUserMatch(email: String, enteredPassword: String): Boolean {
        val user = getUserByEmail(email)
        return user?.password == enteredPassword
    }

    override suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun searchForUsers(query: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        userId: String,
        bannerUrl: String?,
        profileImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(userIds: List<String>): List<User> {
        TODO("Not yet implemented")
    }
}
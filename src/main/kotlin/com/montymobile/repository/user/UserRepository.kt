package com.montymobile.repository.user

import com.montymobile.data.models.User

interface UserRepository {

    suspend fun createUser(user: User)

    suspend fun getUserById(id: String): User?

    suspend fun getUserByEmail(email : String): User?
}
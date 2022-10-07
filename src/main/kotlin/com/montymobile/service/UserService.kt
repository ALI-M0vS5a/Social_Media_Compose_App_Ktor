package com.montymobile.service

import com.montymobile.data.models.User
import com.montymobile.data.repository.follow.FollowRepository
import com.montymobile.data.repository.user.UserRepository
import com.montymobile.data.requests.CreateAccountRequest
import com.montymobile.data.requests.UpdateProfileRequest
import com.montymobile.data.responses.ProfileResponse
import com.montymobile.data.responses.UserResponseItem


class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {

    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun getUserProfile(userId: String, callerUserId: String): ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            userId = user.id,
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            profilePictureUrl = user.profileImageUrl,
            bannerUrl = user.bannerUrl,
            topSkills = user.skills,
            gitHubUrl = user.gitHubUrl,
            instagramUrl = user.instagramUrl,
            linkedinUrl = user.linkedInUrl,
            isOwnProfile = userId == callerUserId,
            isFollowing = if(userId != callerUserId) {
                followRepository.doesUserFollow(callerUserId,userId)
            } else {
                false
            }
        )
    }

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun isValidPassword(enteredPassword: String, actualPassword: String): Boolean {
        return enteredPassword == actualPassword
    }

    suspend fun searchForUsers(query: String, userId: String): List<UserResponseItem> {
        val users = userRepository.searchForUsers(query)
        val followsByUser = followRepository.getFollowsByUser(userId)
        return users.map { user ->
            val isFollowing = followsByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                userId = user.id,
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )
        }.filter { it.userId != userId }
    }


    suspend fun createUser(request: CreateAccountRequest) {
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                bannerUrl = "",
                profileImageUrl = "",
                bio = "",
                gitHubUrl = null,
                instagramUrl = null,
                linkedInUrl = null
            )
        )
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): ValidationEvent {
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }

    suspend fun updateUser(
        userId: String,
        bannerUrl: String?,
        profileImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        return userRepository.updateUser(
            userId = userId,
            bannerUrl = bannerUrl,
            profileImageUrl = profileImageUrl,
            updateProfileRequest = updateProfileRequest
        )
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object Success : ValidationEvent()
    }

}
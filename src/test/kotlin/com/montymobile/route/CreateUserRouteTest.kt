package com.montymobile.route

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.montymobile.data.models.User
import com.montymobile.data.requests.CreateAccountRequest
import com.montymobile.data.responses.BasicApiResponse
import com.montymobile.di.testModule
import com.montymobile.plugins.configureSerialization
import com.montymobile.repository.user.FakeUserRepository
import com.montymobile.util.ApiResponseMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest


internal class CreateUserRouteTest : KoinTest {

    private val userRepository by inject<FakeUserRepository>()

    private val gson = Gson()

    @Before
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }
    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun`Create user, no body attached, responds with BadRequest`() {
        withTestApplication(
            moduleFunction = {
                install(Routing){
                    createUser(userRepository)
                }
            }
        ) {
            val request = handleRequest(
                method = HttpMethod.Post,
                uri = "/api/user/create"
            )
            assertThat(request.response.status()).isEqualTo(HttpStatusCode.BadRequest)
        }
    }

    @Test
    fun `Create user, user already exists, responds with unsuccessful`() = runBlocking {
        val user = User(
            email = "test@test.com",
            username = "test",
            password = "test",
            profileImageUrl = "",
            bio = "",
            gitHubUrl = null,
            instagramUrl = null,
            linkedInUrl = null
        )
        userRepository.createUser(user)
        withTestApplication(
            moduleFunction = {
                configureSerialization()
                install(Routing) {
                    createUser(userRepository)
                }
            }
        ) {
            val request = handleRequest(
                method = HttpMethod.Post,
                uri = "/api/user/create"
            ) {
                addHeader("Content-Type", "application/json")
                val request = CreateAccountRequest(
                    email = "test@test.com",
                    username = "asdf",
                    password = "asdf"
                )
                setBody(gson.toJson(request))
            }

            val response = gson.fromJson(
                request.response.content ?: "",
                BasicApiResponse::class.java
            )
            assertThat(response.successful).isFalse()
            assertThat(response.message).isEqualTo(ApiResponseMessages.USER_ALREADY_EXISTS)
        }
    }

    @Test
    fun `Create user, email is empty, responds with unsuccessful`() = runBlocking {
        val user = User(
            email = "test@test.com",
            username = "test",
            password = "test",
            profileImageUrl = "",
            bio = "",
            gitHubUrl = null,
            instagramUrl = null,
            linkedInUrl = null
        )
        userRepository.createUser(user)
        withTestApplication(
            moduleFunction = {
                configureSerialization()
                install(Routing) {
                    createUser(userRepository)
                }
            }
        ) {
            val request = handleRequest(
                method = HttpMethod.Post,
                uri = "/api/user/create"
            ) {
                addHeader("Content-Type", "application/json")
                val request = CreateAccountRequest(
                    email = "",
                    username = "",
                    password = ""
                )
                setBody(gson.toJson(request))
            }

            val response = gson.fromJson(
                request.response.content ?: "",
                BasicApiResponse::class.java
            )
            assertThat(response.successful).isFalse()
            assertThat(response.message).isEqualTo(ApiResponseMessages.FIELDS_BLANK)
        }
    }

    @Test
    fun `Create user, valid data, responds with successful`() {
        withTestApplication(
            moduleFunction = {
                configureSerialization()
                install(Routing) {
                    createUser(userRepository)
                }
            }
        ) {
            val request = handleRequest(
                method = HttpMethod.Post,
                uri = "/api/user/create"
            ) {
                addHeader("Content-Type", "application/json")
                val request = CreateAccountRequest(
                    email = "test@test.com",
                    username = "test",
                    password = "test"
                )
                setBody(gson.toJson(request))
            }
            val response = gson.fromJson(
                request.response.content ?: "",
                BasicApiResponse::class.java
            )
            assertThat(response.successful).isTrue()

            runBlocking {
                val isUserInDb = userRepository.getUserByEmail("test@test.com") != null
                assertThat(isUserInDb).isTrue()
            }
        }
    }

}
package io.phalconite.phalconite.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class AuthLoginResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    val user: AuthLoggedInUserDto
)

@Serializable
data class AuthLoggedInUserDto(
    val uid: String,
    @SerialName("full_name")
    val fullName: String
)
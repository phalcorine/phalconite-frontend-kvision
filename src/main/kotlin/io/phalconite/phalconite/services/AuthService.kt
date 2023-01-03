package io.phalconite.phalconite.services

import arrow.core.Either
import arrow.core.continuations.either
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.phalconite.phalconite.domain.DomainError
import io.phalconite.phalconite.dto.AuthLoginRequestDto
import io.phalconite.phalconite.dto.AuthLoginResponseDto
import io.phalconite.phalconite.store.loading.AppLoadingService
import io.phalconite.phalconite.util.apiUrl
import io.phalconite.phalconite.util.httpClient

object AuthService {
    suspend fun login(payload: AuthLoginRequestDto): Either<DomainError, AuthLoginResponseDto> = either {
        AppLoadingService.startLoading("Logging In...")
        runCatching {
            val response: AuthLoginResponseDto = httpClient.post {
                url {
                    takeFrom(apiUrl)
                    path("/api/auth/login")
                }
                contentType(ContentType.Application.Json)
                setBody(payload)
            }.body()
            AppLoadingService.stopLoading()
            response
        }.getOrElse {
            console.log("[AuthService::login]")
            console.log(it.message)
            console.log(it)
            AppLoadingService.stopLoading()
            return@either shift(DomainError.ClientError("An error occurred from the server"))
        }
    }
}
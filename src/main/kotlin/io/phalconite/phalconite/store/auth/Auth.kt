package io.phalconite.phalconite.store.auth

import io.kvision.redux.RAction
import io.phalconite.phalconite.dto.AuthLoggedInUserDto

data class AppAuthState(
    val accessToken: String? = null,
    val user: AuthLoggedInUserDto? = null
)

sealed class AppAuthAction : RAction {
    data class Login(
        val accessToken: String,
        val user: AuthLoggedInUserDto
    ) : AppAuthAction()

    object Logout : AppAuthAction()
}

fun appAuthReducer(state: AppAuthState, action: AppAuthAction): AppAuthState = when (action) {
    is AppAuthAction.Login -> {
        state.copy(
            accessToken = action.accessToken,
            user = action.user
        )
    }
    is AppAuthAction.Logout -> {
        state.copy(
            accessToken = null,
            user = null
        )
    }
}
package io.phalconite.phalconite.store

import io.kvision.redux.RAction
import io.kvision.redux.createReduxStore
import io.phalconite.phalconite.store.auth.AppAuthAction
import io.phalconite.phalconite.store.auth.AppAuthState
import io.phalconite.phalconite.store.auth.appAuthReducer
import io.phalconite.phalconite.store.loading.AppLoadingAction
import io.phalconite.phalconite.store.loading.AppLoadingState
import io.phalconite.phalconite.store.loading.appLoadingReducer
import io.phalconite.phalconite.store.notification.AppNotificationAction
import io.phalconite.phalconite.store.notification.AppNotificationState
import io.phalconite.phalconite.store.notification.appNotificationReducer
import io.phalconite.phalconite.store.routing.AppRoutingAction
import io.phalconite.phalconite.store.routing.AppRoutingState
import io.phalconite.phalconite.store.routing.appRoutingReducer

data class AppState(
    val auth: AppAuthState = AppAuthState(),
    val loading: AppLoadingState = AppLoadingState(),
    val notification: AppNotificationState = AppNotificationState(),
    val routing: AppRoutingState = AppRoutingState()
)

fun appReducer(state: AppState, action: RAction): AppState = when (action) {
    is AppAuthAction -> {
        state.copy(
            auth = appAuthReducer(state.auth, action)
        )
    }
    is AppLoadingAction -> {
        state.copy(
            loading = appLoadingReducer(state.loading, action)
        )
    }
    is AppNotificationAction -> {
        state.copy(
            notification = appNotificationReducer(state.notification, action)
        )
    }
    is AppRoutingAction -> {
        state.copy(
            routing = appRoutingReducer(state.routing, action)
        )
    }
    else -> {
        state
    }
}

val appStore = createReduxStore(::appReducer, AppState())
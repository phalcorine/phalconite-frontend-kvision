package io.phalconite.phalconite.store.loading

import io.kvision.redux.RAction
import io.phalconite.phalconite.store.appStore

data class AppLoadingState(
    val state: Boolean = false,
    val label: String = "Loading..."
)

sealed class AppLoadingAction : RAction {
    data class StartLoading(val label: String) : AppLoadingAction()
    object StopLoading : AppLoadingAction()
}

fun appLoadingReducer(state: AppLoadingState, action: AppLoadingAction): AppLoadingState = when (action) {
    is AppLoadingAction.StartLoading -> {
        state.copy(
            state = true,
            label = action.label
        )
    }
    is AppLoadingAction.StopLoading -> {
        state.copy(
            state = false,
            label = "Loading..."
        )
    }
}

object AppLoadingService {
    fun startLoading(label: String) {
        appStore.dispatch(AppLoadingAction.StartLoading(label))
    }

    fun stopLoading() {
        appStore.dispatch(AppLoadingAction.StopLoading)
    }
}
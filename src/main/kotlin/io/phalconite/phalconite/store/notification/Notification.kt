package io.phalconite.phalconite.store.notification

import io.kvision.redux.RAction
import io.kvision.state.sub
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.phalconite.phalconite.store.appStore

enum class AppNotificationType {
    NONE,
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

data class AppNotificationState(
    val type: AppNotificationType = AppNotificationType.NONE,
    val title: String = "Information",
)

sealed class AppNotificationAction : RAction {
    object None : AppNotificationAction()
    data class Success(val title: String) : AppNotificationAction()
    data class Error(val title: String) : AppNotificationAction()
    data class Warning(val title: String) : AppNotificationAction()
    data class Info(val title: String) : AppNotificationAction()
}

fun appNotificationReducer(state: AppNotificationState, action: AppNotificationAction): AppNotificationState = when (action) {
    is AppNotificationAction.None -> {
        state.copy(
            type = AppNotificationType.NONE,
            title = "Information",
        )
    }
    is AppNotificationAction.Success -> {
        state.copy(
            type = AppNotificationType.SUCCESS,
            title = action.title,
        )
    }
    is AppNotificationAction.Error -> {
        state.copy(
            type = AppNotificationType.ERROR,
            title = action.title,
        )
    }
    is AppNotificationAction.Warning -> {
        state.copy(
            type = AppNotificationType.WARNING,
            title = action.title,
        )
    }
    is AppNotificationAction.Info -> {
        state.copy(
            type = AppNotificationType.INFO,
            title = action.title,
        )
    }
}

// Services


object AppNotificationService {
    fun initialize() {
        appStore.sub {
            return@sub it.notification
        }.subscribe {
            when (it.type) {
                AppNotificationType.SUCCESS -> {
                    showSuccess(it.title)
                }
                AppNotificationType.ERROR -> {
                    showError(it.title)
                }
                AppNotificationType.WARNING -> {
                    showWarning(it.title)
                }
                AppNotificationType.INFO -> {
                    showInfo(it.title)
                }
                AppNotificationType.NONE -> {
                    // Do Nothing!
                }
            }
        }
    }

    private fun showSuccess(title: String) {
        Toast.success(title)
        showNone()
    }

    private fun showError(title: String) {
        Toast.danger(title)
        showNone()
    }

    private fun showWarning(title: String) {
        Toast.warning(title)
        showNone()
    }

    private fun showInfo(title: String) {
        Toast.info(title)
        showNone()
    }

    private fun showNone() {
        appStore.dispatch(AppNotificationAction.None)
    }
}
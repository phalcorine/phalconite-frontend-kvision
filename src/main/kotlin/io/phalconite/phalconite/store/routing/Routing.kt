package io.phalconite.phalconite.store.routing

import io.kvision.redux.RAction
import io.phalconite.phalconite.PageRoutes
import io.phalconite.phalconite.routing
import io.phalconite.phalconite.store.appStore

data class AppRoutingState(
    val route: PageRoutes = PageRoutes.HOME
)

sealed class AppRoutingAction : RAction {
    object Home : AppRoutingAction()
    object FirstPage : AppRoutingAction()
    object SecondPage : AppRoutingAction()
    object LoginPage : AppRoutingAction()
}

fun appRoutingReducer(state: AppRoutingState, action: AppRoutingAction): AppRoutingState = when (action) {
    is AppRoutingAction.Home -> {
        state.copy(route = PageRoutes.HOME)
    }
    is AppRoutingAction.FirstPage -> {
        state.copy(route = PageRoutes.FIRST_PAGE)
    }
    is AppRoutingAction.SecondPage -> {
        state.copy(route = PageRoutes.SECOND_PAGE)
    }
    is AppRoutingAction.LoginPage -> {
        state.copy(route = PageRoutes.LOGIN_PAGE)
    }
}

object AppRoutingService {
    fun initialize() {
        routing
            .on(PageRoutes.FIRST_PAGE.url, {
                appStore.dispatch(AppRoutingAction.FirstPage)
            })
            .on(PageRoutes.SECOND_PAGE.url, {
                appStore.dispatch(AppRoutingAction.SecondPage)
            })
            .on(PageRoutes.LOGIN_PAGE.url, {
                appStore.dispatch(AppRoutingAction.LoginPage)
            })
            .on({
                appStore.dispatch(AppRoutingAction.Home)
            })
            .resolve()
    }
}
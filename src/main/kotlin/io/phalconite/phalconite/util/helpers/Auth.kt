package io.phalconite.phalconite.util.helpers

import io.kvision.core.Container
import io.phalconite.phalconite.store.appStore
import io.phalconite.phalconite.store.routing.AppRoutingAction

fun Container.canActivate(block: Container.() -> Unit) {
    val isLoggedIn = appStore.getState().auth.user != null
    console.log("[CanActivate]")
    console.log("Is User Logged In: $isLoggedIn")

    if (isLoggedIn) {
       return block()
    }

    console.log("Not logged in. Going to login page")
    appStore.dispatch(AppRoutingAction.LoginPage)
}
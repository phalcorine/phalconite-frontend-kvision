package io.phalconite.phalconite.pages

import io.kvision.core.Container
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.h4
import io.kvision.html.p
import io.kvision.state.bind
import io.kvision.state.sub
import io.phalconite.phalconite.routing
import io.phalconite.phalconite.store.appStore

fun Container.secondPage() {
    val currentUser = appStore.sub { it.auth.user }
    div {
        h4 { +"This is the second page..." }
        button("Go to First Page").onClick {
            routing.navigate("/first")
        }
        div().bind(currentUser) { maybeUser ->
            maybeUser?.let { user ->
                p { +"User ID: ${user.uid}" }
                p { +"Full Name: ${user.fullName}" }
            }
        }
    }
}
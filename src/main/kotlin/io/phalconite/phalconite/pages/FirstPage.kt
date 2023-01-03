package io.phalconite.phalconite.pages

import io.kvision.core.Container
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.h4
import io.phalconite.phalconite.PageRoutes
import io.phalconite.phalconite.routing

fun Container.firstPage() {
    div {
        h4 { +"This is the first page..." }
        button("Go to Second Page").onClick {
            routing.navigate(PageRoutes.SECOND_PAGE.url)
        }
        button("Go to Login Page").onClick {
            routing.navigate(PageRoutes.LOGIN_PAGE.url)
        }
    }
}
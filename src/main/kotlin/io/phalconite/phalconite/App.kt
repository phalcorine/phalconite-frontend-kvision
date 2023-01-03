package io.phalconite.phalconite

import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.BootstrapModule
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapIconsModule
import io.kvision.DatetimeModule
import io.kvision.FontAwesomeModule
import io.kvision.ImaskModule
import io.kvision.RichTextModule
import io.kvision.ChartModule
import io.kvision.ToastifyModule
import io.kvision.html.div
import io.kvision.html.h3
import io.kvision.html.main
import io.kvision.module
import io.kvision.panel.root
import io.kvision.routing.Routing
import io.kvision.startApplication
import io.kvision.state.bind
import io.kvision.state.sub
import io.phalconite.phalconite.pages.firstPage
import io.phalconite.phalconite.pages.loginPage
import io.phalconite.phalconite.pages.secondPage
import io.phalconite.phalconite.store.appStore
import io.phalconite.phalconite.store.notification.AppNotificationService
import io.phalconite.phalconite.store.routing.AppRoutingAction
import io.phalconite.phalconite.store.routing.AppRoutingService
import io.phalconite.phalconite.util.helpers.canActivate

val routing = Routing.init()

class App : Application() {

    init {
        bootstrap()
    }

    override fun start() {

        root("kvapp") {
            div("Hello world")
            div().bind(appStore.sub {
                return@sub it.loading
            }) { state ->
                if (state.state) {
                    h3 { +state.label }
                }
            }
            main().bind(appStore.sub {
                return@sub it.routing
            }) { state ->
                when (state.route) {
                    PageRoutes.HOME -> {
                        firstPage()
                    }
                    PageRoutes.FIRST_PAGE -> {
                        firstPage()
                    }
                    PageRoutes.SECOND_PAGE -> {
                        canActivate {
                            console.log("We should be in the second page now")
                            secondPage()
                        }
                    }
                    PageRoutes.LOGIN_PAGE -> {
                        loginPage()
                    }
                }
            }
        }
    }

    private fun bootstrap() {
        // Notifications
        AppNotificationService.initialize()

        // Routing
        AppRoutingService.initialize()
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapIconsModule,
        DatetimeModule,
        FontAwesomeModule,
        ImaskModule,
        RichTextModule,
        ChartModule,
        ToastifyModule,
        CoreModule
    )
}

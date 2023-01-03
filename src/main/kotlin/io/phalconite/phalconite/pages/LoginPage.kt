package io.phalconite.phalconite.pages

import arrow.core.continuations.either
import io.kvision.core.Container
import io.kvision.form.formPanel
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.html.InputType
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.toolbar.buttonGroup
import io.phalconite.phalconite.dto.AuthLoginRequestDto
import io.phalconite.phalconite.routing
import io.phalconite.phalconite.services.AuthService
import io.phalconite.phalconite.store.appStore
import io.phalconite.phalconite.store.auth.AppAuthAction
import io.phalconite.phalconite.store.notification.AppNotificationAction
import io.phalconite.phalconite.store.routing.AppRoutingAction
import io.phalconite.phalconite.util.helpers.withProgress
import io.phalconite.phalconite.util.mainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class LoginForm(
    val email: String?,
    val password: String?
)

fun Container.loginPage() {
    div {
        val form = formPanel {
            add(
                LoginForm::email,
                Text(label = "Email") {
                    type = InputType.EMAIL
                    placeholder = "Enter email address..."
                },
                validator = {
                    validateEmail(it.getValue())?.let { return@let false }
                },
                validatorMessage = {
                    validateEmail(it.getValue())
                }
            )
            add(
                LoginForm::password,
                Password(label = "Password") {
                    placeholder = "Enter password..."
                },
                validator = {
                    validatePassword(it.getValue())?.let { return@let false }
                },
                validatorMessage = {
                    validatePassword(it.getValue())
                }
            )
        }
        buttonGroup {
            button("Login").onClick {
                val isFormValid = form.validate()
                if (!isFormValid) return@onClick
                val formData = form.getData()

                console.log("[LoginForm]")
                console.log(formData)

                // Make network request to server!
                mainScope.launch {
                    either {
                        val response = AuthService.login(
                            AuthLoginRequestDto(
                                email = formData.email!!,
                                password = formData.password!!
                            )
                        ).bind()
                        console.log("Login Successful!!!")
                        console.log(response)
                        appStore.dispatch(AppAuthAction.Login(
                            accessToken = response.accessToken,
                            user = response.user
                        ))
                        appStore.dispatch(AppNotificationAction.Success("Login Successful!"))
                        appStore.dispatch(AppRoutingAction.SecondPage)
                    }.mapLeft {
                        appStore.dispatch(AppNotificationAction.Success(it.message))
                    }
                }
            }
        }
    }

    addAfterInsertHook {
        // Do jQuery here to load BlockUI, if possible
    }
}

fun validateEmail(email: String?): String? {
    if (email == null) return "Email can not be empty!"
    if (email.isBlank()) return "Email can not be blank!"
    if (!email.contains("@")) return "Not a valid email address!"
    return null
}

fun validatePassword(password: String?): String? {
    if (password == null) return "Password can not be empty!"
    if (password.isBlank()) return "Password can not be blank!"
    if (password.length < 8) return "Password can not be less than eight (8) characters!"
    return null
}
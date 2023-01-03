package io.phalconite.phalconite.domain

sealed interface DomainError {
    val message: String
    data class ClientError(val msg: String) : DomainError {
        override val message: String
            get() = msg
    }
}
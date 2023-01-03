package io.phalconite.phalconite.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

val mainScope = MainScope()

val defaultScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
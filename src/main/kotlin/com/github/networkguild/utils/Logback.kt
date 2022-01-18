package com.github.networkguild.utils

import org.slf4j.LoggerFactory

object Logback {
    inline operator fun <reified T> invoke() = lazy {
        LoggerFactory.getLogger(T::class.java)
    }
}

package com.github.networkguild.framework

interface Command<T> {

    val name: String
        get() = javaClass.simpleName.lowercase()

    val properties: CommandProperties
        get() = javaClass.getAnnotation(CommandProperties::class.java)

    suspend fun handle(event: T)
}

package com.turastory.simpleapp.util

interface Logger {
    fun log(message: String, tag: String, level: Level)

    fun e(message: String, tag: String = "No Tag") =
        log(message, tag, level = Level.ERROR)

    fun d(message: String, tag: String = "No Tag") =
        log(message, tag, level = Level.ERROR)

    enum class Level {
        DEBUG, ERROR
    }
}
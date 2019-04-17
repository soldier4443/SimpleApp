package com.turastory.simpleapp

import com.turastory.simpleapp.util.Logger

class TestLoggerImpl : Logger {
    override fun log(message: String, tag: String, level: Logger.Level) {
        println("[${level.name}] $tag: $message")
    }
}
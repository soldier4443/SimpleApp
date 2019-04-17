package com.turastory.simpleapp.util

import android.util.Log

class LoggerImpl : Logger {
    override fun log(message: String, tag: String, level: Logger.Level) {
        val priority = when (level) {
            Logger.Level.DEBUG -> Log.DEBUG
            Logger.Level.ERROR -> Log.ERROR
        }

        Log.println(priority, tag, message)
    }
}
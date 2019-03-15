package com.turastory.simpleapp.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Exec {
    companion object {
        val background: Executor = Executors.newSingleThreadExecutor()
        val main: Executor = object : Executor {
            private val handler = Handler(Looper.getMainLooper())

            override fun execute(command: Runnable) {
                handler.post(command)
            }
        }
    }
}
package com.turastory.simpleapp.ui

/**
 * From android developers medium post
 */
class SimpleEvent {

    var handled = false
        private set

    fun runIfNotHandled(func: () -> Unit) {
        if (!handled)
            func()
    }
}
package com.turastory.simpleapp.data.source

enum class State {
    LOADING,
    LOADED,
    FAILED
}

class NetworkState private constructor(
    val state: State,
    val message: String = ""
) {
    companion object {
        val LOADING = NetworkState(State.LOADING)
        val LOADED = NetworkState(State.LOADED)

        fun error(message: String) =
            NetworkState(State.FAILED, message)

        fun error(throwable: Throwable) =
            NetworkState(
                State.FAILED,
                message = throwable.message ?: "Unknown Error"
            )
    }
}
package com.turastory.simpleapp.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple trick to easily handle network requests
 */
class RequestWrapper<T>(private val originalCall: Call<T>) {

    private var successTask: (T?) -> Unit = { }
    private var failedTask: (String) -> Unit = { }

    fun doOnSuccess(task: (T?) -> Unit): RequestWrapper<T> {
        this.successTask = task
        return this
    }

    fun doOnFailed(task: (String) -> Unit): RequestWrapper<T> {
        this.failedTask = task
        return this
    }

    fun done() {
        originalCall.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                failedTask(t.message ?: "Unknown Error")
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    successTask(response.body())
                } else {
                    failedTask("Error code: ${response.code()}")
                }
            }
        })
    }
}

// Additional extension functions

fun <T> Call<T>.doOnSuccess(task: (T?) -> Unit): RequestWrapper<T> {
    return RequestWrapper(this).doOnSuccess(task)
}

fun <T> Call<T>.doOnFailed(task: (String) -> Unit): RequestWrapper<T> {
    return RequestWrapper(this).doOnFailed(task)
}
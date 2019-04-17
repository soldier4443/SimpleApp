package com.turastory.simpleapp.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val defaultGsonInstance = Gson()

inline fun <reified T> String.fromJson(gson: Gson = defaultGsonInstance): T {
    return if (Collection::class.java.isAssignableFrom(T::class.java)) {
        gson.fromJson<T>(this, object : TypeToken<T>() {}.type)
    } else {
        gson.fromJson<T>(this, T::class.java)
    }
}

inline fun <reified T> T.toJson(gson: Gson = defaultGsonInstance): String {
    return gson.toJson(this)
}
package com.turastory.simpleapp.ext

import android.app.Activity
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Kotlin extension functions
 */

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide(gone: Boolean = false) {
    this.visibility = if (gone) View.GONE else View.INVISIBLE
}
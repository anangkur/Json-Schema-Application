package com.anangkur.jsonschemeapplication.utils.extensions

import android.view.View
import androidx.core.content.ContextCompat

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 23.45
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if(value) View.VISIBLE else View.GONE
    }

fun View.setDrawable(drawable: Int) {
    this.background = ContextCompat.getDrawable(context, drawable)
}

fun View.debouncedListener(listener: ViewOnDebounceListener) {
    this.debouncedListener {
        listener.onClickDebounce(it)
    }
}

fun View.debouncedListener(debounceIntervalMs: Int = 700, listener: ViewOnDebounceListener) {
    this.debouncedListener(debounceIntervalMs) {
        listener.onClickDebounce(it)
    }
}

fun View.debouncedListener(debounceIntervalMs: Int = 700, listener: (view: View?) -> Unit) {
    var lastClickMap: Long? = 0
    val customListener = View.OnClickListener {
        val previousClickTimestamp = lastClickMap
        val currentTime = System.currentTimeMillis()
        lastClickMap = currentTime
        if (
            previousClickTimestamp == null ||
            currentTime - previousClickTimestamp.toLong() > debounceIntervalMs
        ) {
            listener(it)
        }
    }
    this.setOnClickListener(customListener)
}

interface ViewOnDebounceListener {
    fun onClickDebounce(view: View?)
}
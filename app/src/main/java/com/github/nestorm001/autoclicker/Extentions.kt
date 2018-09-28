package com.github.nestorm001.autoclicker

import android.util.Log

/**
 * Created on 2018/9/28.
 * By nesto
 */
private const val TAG = "AutoClickService"

fun Any.logd(tag: String = TAG) {
    if (this is String) {
        Log.d(tag, this)
    } else {
        Log.d(tag, this.toString())
    }
}

fun Any.loge(tag: String = TAG) {
    if (this is String) {
        Log.e(tag, this)
    } else {
        Log.e(tag, this.toString())
    }
}
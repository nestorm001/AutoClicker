package com.github.nestorm001.autoclicker

import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View


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

fun View.performActionDown(x: Float, y: Float) {
    val newTouch = MotionEvent.obtain(SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),
            MotionEvent.ACTION_DOWN,
            x, y, 0)
    dispatchTouchEvent(newTouch)
}
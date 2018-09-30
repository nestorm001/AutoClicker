package com.github.nestorm001.autoclicker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

/**
 * Created on 2018/9/28.
 * By nesto
 */

var autoClickService: AutoClickService? = null

class AutoClickService : AccessibilityService() {

    override fun onInterrupt() {
        // NO-OP
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // NO-OP
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        "onServiceConnected".logd()
        autoClickService = this
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun click(x: Int, y: Int) {
        "click $x $y".logd()
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
        val builder = GestureDescription.Builder()
        val gestureDescription = builder
                .addStroke(GestureDescription.StrokeDescription(path, 10, 10))
                .build()
        dispatchGesture(gestureDescription, null, null)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        "AutoClickService onUnbind".logd()
        autoClickService = null
        return super.onUnbind(intent)
    }

    
    override fun onDestroy() {
        "AutoClickService onDestroy".logd()
        autoClickService = null
        super.onDestroy()
    }
}
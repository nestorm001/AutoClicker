package com.github.nestorm001.autoclicker

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Created on 2018/9/28.
 * By nesto
 */


class AutoClickService : AccessibilityService() {
    companion object {
        val instance = this
    }

    val bounds = Rect()
    override fun onInterrupt() {
        // NO-OP
    }

    private fun childCount(info: AccessibilityNodeInfo?) = info?.childCount ?: 0

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        "onAccessibilityEvent".logd()
        printLeaves(event.source)
    }

    private fun printLeaves(info: AccessibilityNodeInfo?) {
        "${info?.className}".logd()
        if (childCount(info) == 0 && info?.text != null) {
            if (info.className.contains("Button")) {
                info.getBoundsInScreen(bounds)
                click(bounds.centerX(), bounds.centerY())
            }
        } else {
            printLeavesForEachChildIn(info)
        }
    }

    private fun printLeavesForEachChildIn(info: AccessibilityNodeInfo?) {
        for (i in 0 until childCount(info)) {
            printLeaves(info?.getChild(i))
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startActivity(Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun click(x: Int, y: Int) {
        "click $x $y".logd()
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
//        path.lineTo(x + 1f, y + 1f)
        val builder = GestureDescription.Builder()
        val gestureDescription = builder
                .addStroke(GestureDescription.StrokeDescription(path, 0, 50))
                .build()
        dispatchGesture(gestureDescription, null, null)
    }
}
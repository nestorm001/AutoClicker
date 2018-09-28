package com.github.nestorm001.autoclicker

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Created on 2018/9/28.
 * By nesto
 */


class AutoClickService : AccessibilityService() {

    override fun onInterrupt() {
        // NO-OP
    }

    private fun childCount(info: AccessibilityNodeInfo?) = info?.childCount ?: 0

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        printLeaves(event.source)
    }

    private fun printLeaves(info: AccessibilityNodeInfo?) {
        if (childCount(info) == 0 && info?.text != null) {
            val text = info.text
            text.logd()
            if (text == "1") {
                "performAction".logd()
                info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
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
}
package com.github.nestorm001.autoclicker.bean

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Point

/**
 * Created on 2018/10/19.
 * By nesto
 */
abstract class Event {
    var startTime = 10L
    var duration = 10L
    lateinit var path: Path
    fun onEvent(): GestureDescription.StrokeDescription {
        path = Path()
        movePath()
        return GestureDescription.StrokeDescription(path, startTime, duration)
    }

    abstract fun movePath()
}

data class Move(val to: Point) : Event() {
    override fun movePath() {
        path.moveTo(to.x.toFloat(), to.y.toFloat())
    }
}

data class Click(val to: Point) : Event() {
    override fun movePath() {
        path.moveTo(to.x.toFloat(), to.y.toFloat())
    }
}

data class Swipe(val from: Point, val to: Point) : Event() {
    override fun movePath() {
        path.moveTo(from.x.toFloat(), from.y.toFloat())
        path.lineTo(to.x.toFloat(), to.y.toFloat())
    }
}
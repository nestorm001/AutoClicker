package com.github.nestorm001.autoclicker

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

/**
 * Created on 2018/9/30.
 * By nesto
 */
class TouchAndDragListener(private val params: WindowManager.LayoutParams,
                           private val startDragDistance: Int = 10,
                           private val onTouch: Action?,
                           private val onDrag: Action?) : View.OnTouchListener {
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0.toFloat()
    private var initialTouchY: Float = 0.toFloat()
    private var isDrag = false

    private fun isDragging(event: MotionEvent): Boolean =
            ((Math.pow((event.rawX - initialTouchX).toDouble(), 2.0)
                    + Math.pow((event.rawY - initialTouchY).toDouble(), 2.0))
                    > startDragDistance * startDragDistance)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                isDrag = false
                initialX = params.x
                initialY = params.y
                initialTouchX = event.rawX
                initialTouchY = event.rawY
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isDrag && isDragging(event)) {
                    isDrag = true
                }
                if (!isDrag) return true
                params.x = initialX + (event.rawX - initialTouchX).toInt()
                params.y = initialY + (event.rawY - initialTouchY).toInt()
                onDrag?.invoke()
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (!isDrag) {
                    onTouch?.invoke()
                    return true
                }
            }
        }
        return false
    }
}
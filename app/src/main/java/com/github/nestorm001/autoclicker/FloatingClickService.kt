package com.github.nestorm001.autoclicker

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer


/**
 * Created on 2018/9/28.
 * By nesto
 */
class FloatingClickService : Service() {
    private lateinit var manager: WindowManager
    private lateinit var view: View
    private val location = IntArray(2)
    private var startDragDistance: Int = 0
    private var timer: Timer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startDragDistance = dp2px(10f)
        view = LayoutInflater.from(this).inflate(R.layout.widget, null)

        //setting the layout parameters
        val overlayParam =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    WindowManager.LayoutParams.TYPE_PHONE
                }
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                overlayParam,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)


        //getting windows services and adding the floating view to it
        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.addView(view, params)

        //adding an touchlistener to make drag movement of the floating widget
        view.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()
            private var isDrag = false

            private fun isDragging(event: MotionEvent): Boolean =
                    ((Math.pow((event.rawX - initialTouchX).toDouble(), 2.0)
                            + Math.pow((event.rawY - initialTouchY).toDouble(), 2.0))
                            > startDragDistance * startDragDistance)

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
                        manager.updateViewLayout(view, params)
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        if (!isDrag) {
                            viewOnClick()
                            return true
                        }
                    }
                }
                return false
            }
        })
    }

    private var isOn = false
    private fun viewOnClick() {
        if (isOn) {
            timer?.cancel()
        } else {
            timer = fixedRateTimer(initialDelay = 0,
                    period = 200) {
                view.getLocationOnScreen(location)
                autoClickService?.click(location[0] + view.right + 10,
                        location[1] + view.bottom + 10)
            }
        }
        isOn = !isOn
        (view as TextView).text = "${if (isOn) "ON" else "OFF"}"

    }

    override fun onDestroy() {
        super.onDestroy()
        manager.removeView(view)
    }
}
package com.github.nestorm001.autoclicker

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager


/**
 * Created on 2018/9/28.
 * By nesto
 */
class FloatingClickService : Service() {
    private lateinit var manager: WindowManager
    private lateinit var view: View

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        view = LayoutInflater.from(this).inflate(R.layout.widget, null)


        //setting the layout parameters
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT)


        //getting windows services and adding the floating view to it
        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.addView(view, params)
        

        //adding an touchlistener to make drag movement of the floating widget
//        view.setOnTouchListener(object : View.OnTouchListener {
//            private var initialX: Int = 0
//            private var initialY: Int = 0
//            private var initialTouchX: Float = 0.toFloat()
//            private var initialTouchY: Float = 0.toFloat()
//
//            override fun onTouch(v: View, event: MotionEvent): Boolean {
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        initialX = params.x
//                        initialY = params.y
//                        initialTouchX = event.rawX
//                        initialTouchY = event.rawY
//                        return true
//                    }
//
//                    MotionEvent.ACTION_MOVE -> {
//                        //this code is helping the widget to move around the screen with fingers
//                        params.x = initialX + (event.rawX - initialTouchX).toInt()
//                        params.y = initialY + (event.rawY - initialTouchY).toInt()
//                        manager.updateViewLayout(view, params)
//                        return true
//                    }
//                }
//                return false
//            }
//        })
        view.post {
            val height = view.height
            val width = view.width
            view.performActionDown(width - 100f, height - 100f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.removeView(view)
    }
}
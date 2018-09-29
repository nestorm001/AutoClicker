package com.github.nestorm001.autoclicker

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * Created on 2016/7/8.
 * By nesto
 */
class TransparentViewGroup constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        "dispatchTouchEvent".logd()
        // 消费
        //        return true;
        // ====> activity onTouchEvent 消费
        //        return false;
        // 传递 ====> ViewGroup onInterceptTouchEvent
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        "onInterceptTouchEvent".logd()
        // 传递 ===> view dispatchTouchEvent
        return super.onInterceptTouchEvent(ev);
//        return false
        // 传递 ===> ViewGroup onTouchEvent
        //        return true;
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        "onTouchEvent".logd()
        // 传递 ====> activity onTouchEvent
        return super.onTouchEvent(event);
        //        return false;
        // 消费
//        return true
    }
}

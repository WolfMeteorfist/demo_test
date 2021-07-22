package com.example.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 *   @author yuechou.zhang
 *   @since  2021/7/21
 */
/**
 * Created by yuechou.zhang
 * on 2021/7/21
 */
class MyRelativeLayout : RelativeLayout {
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {

    }

    private val TAG = "MyRelativeLayout"

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent-event--${event?.actionMasked}")
        val dispatchTouchEvent = super.dispatchTouchEvent(event)
        Log.d(TAG, "dispatchTouchEvent-event--${event?.actionMasked}, result: $dispatchTouchEvent")
        return dispatchTouchEvent
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent-event--${ev?.actionMasked}")
        val onInterceptTouchEvent = super.onInterceptTouchEvent(ev)
        Log.d(TAG, "onInterceptTouchEvent-event--${ev?.actionMasked}, result: $onInterceptTouchEvent")
        return onInterceptTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent-event--${event?.actionMasked}")
        val onTouchEvent = super.onTouchEvent(event)
        Log.d(TAG, "onTouchEvent-event--${event?.actionMasked}, result: $onTouchEvent")
        return onTouchEvent
    }

}
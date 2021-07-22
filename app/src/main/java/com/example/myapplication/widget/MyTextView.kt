package com.example.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication.utils.ReflectionUtils

/**
 *   @author yuechou.zhang
 *   @since  2021/7/21
 */
/**
 * Created by yuechou.zhang
 * on 2021/7/21
 */
class MyTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {

        setOnClickListener {
            Log.d("zyc", "我幹幹幹幹")
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setOnClickListener {
            Log.d("zyc", "我幹幹幹幹")
        }
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        setOnClickListener {
            Log.d("zyc", "我幹幹幹幹")
        }

    }

    private val TAG = "MyTextView"
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent${event?.actionMasked}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent:${event?.actionMasked}")
        if (event?.actionMasked == MotionEvent.ACTION_UP) {

            //true
            Log.d("zyc", "isEnabled:${isEnabled}")
            //true
            Log.d("zyc", "isFocusable:${isFocusable}")
            //true
            Log.d("zyc", "isClickable:${isClickable}")
            //false
            Log.d("zyc", "isFocusableInTouchMode:${isFocusableInTouchMode}")
            //true
            Log.d("zyc", "!isFocused:${!isFocused}")
            //true
            Log.d("zyc", "isPressed:${isPressed}")

            val editorFields = ReflectionUtils.getDeclaredField(
                this, "mEditor"
            )
            ReflectionUtils.makeAccessible(editorFields)
            //null
            val editorObj = editorFields.get(this)
            Log.d("zyc", "flagFields:${editorFields}")
            Log.d("zyc", "editorObj:${editorObj}")


            val longPressField = ReflectionUtils.getDeclaredField(this, "mHasPerformedLongPress")
            ReflectionUtils.makeAccessible(longPressField)
            val hasLongPressObj = longPressField.get(this)
            Log.d("zyc", "hasLongPressObj:${hasLongPressObj}")

            val mIgnoreNextUpEventField = ReflectionUtils.getDeclaredField(this, "mIgnoreNextUpEvent")
            ReflectionUtils.makeAccessible(mIgnoreNextUpEventField)
            val mIgnoreNextUpEventObj = longPressField.get(this)
            Log.d("zyc", "mIgnoreNextUpEventObj:${mIgnoreNextUpEventObj}")
            val onTouchEvent = super.onTouchEvent(event)
            return onTouchEvent

        }
        val onTouchEvent = super.onTouchEvent(event)
        return onTouchEvent
    }

}
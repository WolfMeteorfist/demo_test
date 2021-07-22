package com.example.myapplication.footrv

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

/**
 *   @author yuechou.zhang
 *   @since  2021/7/20
 */
/**
 * Created by yuechou.zhang
 * on 2021/7/20
 */
class FootRvDecoration :
    RecyclerView.ItemDecoration() {

    lateinit var iotHeader: View

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnItemTouchListener(itemTouchListener)
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            measureHoverView(recyclerView, iotHeader)
        }
        iotHeader = LayoutInflater.from(recyclerView.context)
            .inflate(R.layout.item_iot_header_foot, recyclerView, false)
    }

    private fun isTouchOnHeaderView(x: Float, y: Float): Boolean {
        var result = false
        iotHeader.let {
            result = y > it.top && y < it.bottom && x > it.left && x < it.right
        }
        return result
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (isIotHeaderOverTop(parent)) {
            //正常的话是无法传递点击事件
//            iotHeader.findViewById<TextView>(R.id.tv_cancle)?.setOnClickListener { v ->
//                Log.d("zyc", "我click了")
//            }
            iotHeader.draw(c)
            iotHeader.setOnClickListener {
                Log.d("zyc", "我被点击了！")
            }
            Log.d("zyc", "iotHeader.isClickable${iotHeader.isClickable}")
            Log.d("zyc", "tvCancel.isClickable${iotHeader.findViewById<TextView>(R.id.tv_cancle).isClickable}")
        }
    }

    /**
     * 测量 View, 确定宽高和绘制坐标
     * */
    var measureHoverView: (parent: RecyclerView, hoverView: View?) -> Unit = { parent, hoverView ->
        hoverView?.apply {
            val params = layoutParams

            val widthSize: Int
            val widthMode: Int
            when (params.width) {
                -1 -> {
                    widthSize = parent.measuredWidth
                    widthMode = View.MeasureSpec.EXACTLY
                }
                else -> {
                    widthSize = parent.measuredWidth
                    widthMode = View.MeasureSpec.AT_MOST
                }
            }

            val heightSize: Int
            val heightMode: Int
            when (params.height) {
                -1 -> {
                    heightSize = parent.measuredHeight
                    heightMode = View.MeasureSpec.EXACTLY
                }
                else -> {
                    heightSize = params.height
                    heightMode = View.MeasureSpec.AT_MOST
                }
            }

            //标准方法1
            measure(
                View.MeasureSpec.makeMeasureSpec(widthSize, widthMode),
                View.MeasureSpec.makeMeasureSpec(heightSize, heightMode)
            )
            //标准方法2
            layout(0, 0, measuredWidth, measuredHeight)
        }
    }


    var isDownInHoverItem: Boolean = false

    private val itemTouchListener = object : RecyclerView.SimpleOnItemTouchListener() {

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val action = e.actionMasked
            if (action == MotionEvent.ACTION_DOWN) {
                isDownInHoverItem = isTouchOnHeaderView(e.x, e.y) && isIotHeaderOverTop(rv)
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                isDownInHoverItem = false
            }

            return isDownInHoverItem
        }

        override fun onTouchEvent(recyclerView: RecyclerView, event: MotionEvent) {
            if (isDownInHoverItem) {
                Log.d("zyc", "isDownInHoverItem:${isDownInHoverItem}, event:${event.actionMasked}")
                //一定要调用dispatchTouchEvent, 否则ViewGroup里面的子View, 不会响应touchEvent
                iotHeader.dispatchTouchEvent(event)
                if (iotHeader is ViewGroup) {
                    if ((iotHeader as ViewGroup).onInterceptTouchEvent(event)) {
                        Log.d("zyc", "onTouchEvent")
                        iotHeader.onTouchEvent(event)
                    }
                } else {
                    Log.d("zyc", "onTouchEvent")
                    iotHeader.onTouchEvent(event)
                }
            }
        }
    }

    fun isIotHeaderOverTop(recyclerView: RecyclerView): Boolean {
        recyclerView.layoutManager?.let {
            if (it is LinearLayoutManager) {
                val firstCompleteVisiblePosition =
                    it.findFirstCompletelyVisibleItemPosition()
                if (firstCompleteVisiblePosition > 1) {
                    return true
                }
            }
        }
        return false
    }

    interface OnCancelClickListener {
        fun cancel();
    }

}
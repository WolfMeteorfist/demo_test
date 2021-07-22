package com.example.myapplication.footrv

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bean.User

/**
 *   @author yuechou.zhang
 *   @since  2021/7/20
 */
/**
 * Created by yuechou.zhang
 * on 2021/7/20
 */
class FootRvDecorationNew(
    private val selectedDeviceList: List<User>,
    private val footActivity: FootActivity
) : RecyclerView.ItemDecoration() {

    lateinit var iotHeaderView: View
    lateinit var iotItemViewForMeasure: View
    private var rvTop = 0
    private var rvLeft = 0
    lateinit var recyclerView: RecyclerView
    private val iotViewList = ArrayList<View>()
    private var first: Boolean = true

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (first) {
            first = false
            measureHoverView(parent, iotItemViewForMeasure)
            measureHoverView(parent, iotHeaderView)
        }
        if (parent.getChildLayoutPosition(view) == 1) {
            outRect.bottom = iotItemViewForMeasure.height * selectedDeviceList.size
        }
    }

    private fun bindDeviceUnderHeader(parent: RecyclerView, user: User, index: Int) {
        Log.d("zyc", "准备绑定Device.")
        val decorView = footActivity.window.decorView as FrameLayout
        val iotItemView = iotViewList[index]
        val params = iotItemView.layoutParams as FrameLayout.LayoutParams
        iotItemView.findViewById<TextView>(R.id.tv_device_item_foot)?.text = user.name
        iotItemView.findViewById<TextView>(R.id.tv_room_item_foot)?.text = user.age.toString()
        if (isIotHeaderOverTop(parent)) {
            params.topMargin = rvTop + iotHeaderView.height + iotItemViewForMeasure.height * index
            params.marginStart = rvLeft
            params.marginEnd = rvLeft
        } else {
            val iotHeaderViewHolder = parent.findViewHolderForLayoutPosition(1)
            iotHeaderViewHolder?.itemView?.apply {
                val intArray = IntArray(2)
                getLocationInWindow(intArray)
                rvLeft = intArray[0]
                val top = intArray[1]
                params.topMargin = top + iotHeaderView.height + iotItemViewForMeasure.height * index
                params.marginStart = rvLeft
                params.marginEnd = rvLeft
            }
        }
        if (decorView.indexOfChild(iotItemView) == -1) {
            decorView.addView(iotItemView, index + 1, params)
        } else {
            decorView.updateViewLayout(iotItemView, params)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val frameLayout = footActivity.window.decorView as FrameLayout
        if (isIotHeaderOverTop(parent)) {
            val layoutParams = iotHeaderView.layoutParams as FrameLayout.LayoutParams
            layoutParams.topMargin = rvTop
            layoutParams.marginStart = rvLeft
            layoutParams.marginEnd = rvLeft
            iotHeaderView.visibility = View.VISIBLE
            if (frameLayout.indexOfChild(iotHeaderView) != -1) {
                frameLayout.updateViewLayout(iotHeaderView, layoutParams)
            } else {
                frameLayout.addView(iotHeaderView, layoutParams)
            }
        } else {
            frameLayout.post {
                frameLayout.removeView(iotHeaderView)
            }
        }

        for ((index, device) in selectedDeviceList.withIndex()) {
            bindDeviceUnderHeader(parent, device, index)
        }

    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        iotHeaderView = LayoutInflater.from(recyclerView.context)
            .inflate(
                R.layout.item_iot_header_foot,
                footActivity.window.decorView as FrameLayout,
                false
            )

        iotItemViewForMeasure = LayoutInflater.from(recyclerView.context)
            .inflate(
                R.layout.item_iot_foot,
                footActivity.window.decorView as FrameLayout,
                false
            )

        repeat(2) {
            iotViewList.add(
                LayoutInflater.from(recyclerView.context)
                    .inflate(
                        R.layout.item_iot_foot,
                        footActivity.window.decorView as FrameLayout,
                        false
                    )
            )
        }

        iotHeaderView.apply {
            findViewById<TextView>(R.id.tv_cancle)
                ?.setOnClickListener {
                    Toast.makeText(footActivity, "点击了流弊", Toast.LENGTH_SHORT)
                        .show()
                }
            setOnClickListener {
                Toast.makeText(footActivity, "点击了widget", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        recyclerView.post {
            val intArray = IntArray(2)
            recyclerView.getLocationInWindow(intArray)
            rvTop = intArray[1]
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
                ViewGroup.LayoutParams.MATCH_PARENT -> {
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
                ViewGroup.LayoutParams.MATCH_PARENT -> {
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

    private fun isIotHeaderOverTop(recyclerView: RecyclerView): Boolean {
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

}
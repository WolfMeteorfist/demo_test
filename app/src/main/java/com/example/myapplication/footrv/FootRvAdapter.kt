package com.example.myapplication.footrv

import android.content.ContentResolver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bean.User
import com.example.myapplication.databinding.ItemIotFootBinding
import com.example.myapplication.databinding.ItemIotHeaderFootBinding
import com.example.myapplication.databinding.ItemMusicHeaderFootBinding
import com.example.myapplication.mvvm.BaseViewHolderKt
import java.lang.ref.WeakReference

/**
 * main page fragment under view recyclerview adapter
 *
 * @author yuechou.zhang
 * @since 2021-07-19
 */
class FootRvAdapter(
    /**
     * 通过iot service获取到的所有可视iot设备
     */
    val list: MutableList<User>,
    val footRvDecoration: FootRvDecorationThird,
    private val stickyViewHolders: ArrayList<StickyViewHolder>,
    private val selectedUserList: MutableList<User>,
    footActivity: FootActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mainPageFragmentWR: WeakReference<FootActivity> = WeakReference(footActivity)

    val contentResolver: ContentResolver? = footActivity.contentResolver

    /**
     * 智能家居顶部Header
     */
    private val mTypeIotHeader = 1

    /**
     * 最顶部的Music Ctr Header
     */
    private val mTypeMusicHeader = 2

    /**
     * 具体设备 type
     */
    private val mTypeIotItem = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            mTypeMusicHeader -> MusicHeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_music_header_foot, parent, false)
            )
            mTypeIotHeader ->
                IotHeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_iot_header_foot, parent, false)
                )
            else -> IotItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_iot_foot, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val filterList: MutableList<User> = list.filter { user ->
            !selectedUserList.contains(user)
        }.toMutableList()
        filterList.addAll(0, selectedUserList)
        if (holder is IotHeaderViewHolder) {
            if (holder.show() && !stickyViewHolders.contains(holder)) {
                stickyViewHolders.add(0, holder)
            }
        }
        if (holder is IotItemViewHolder) {
            val user = filterList[position - 2]
            holder.bindData(user, selectedUserList.indexOf(user))
            if (holder.show() && !stickyViewHolders.contains(holder)) {
                stickyViewHolders.add(holder)
            }
        }
    }

    override fun getItemCount(): Int = list.size + 2

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> mTypeMusicHeader
            1 -> mTypeIotHeader
            else -> mTypeIotItem
        }

    /**
     * 音乐Header holder
     * @property binding ItemMusicHeaderFootBinding
     */
    inner class MusicHeaderViewHolder(itemView: View) :
        BaseViewHolderKt<ItemMusicHeaderFootBinding>(itemView) {

        init {
        }

    }

    /**
     * iot设备头部Header holder
     */
    inner class IotHeaderViewHolder(itemView: View) :
        BaseViewHolderKt<ItemIotHeaderFootBinding>(itemView), StickyViewHolder {
        init {
            Log.d("zyc", "itemView=$itemView")
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "hello", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getOrder(): Int = 0

        override fun layoutId(): Int = R.layout.item_iot_header_foot

        override fun show(): Boolean = true

    }

    /**
     * iot设备列表holder
     */
    inner class IotItemViewHolder(itemView: View) :
        BaseViewHolderKt<ItemIotFootBinding>(itemView), StickyViewHolder {

        var index = -1

        /**
         * bind data to view
         *
         * @param user ApplianceShowBean 设备信息
         */
        fun bindData(user: User, index: Int) {
            this.index = index

            binding?.apply {
                tvDeviceItemFoot.text = user.name
                tvRoomItemFoot.text = user.age.toString()
                cbItemFoot.isChecked = user.check
            }
            binding?.cbItemFoot?.setOnClickListener {
                user.check = binding.cbItemFoot.isChecked
                if (user.check) {
                    selectedUserList.add(0, user)
                } else {
                    selectedUserList.remove(user)
                }
                stickyViewHolders.clear()
                footRvDecoration.changed()
                notifyDataSetChanged()
            }

        }

        override fun getOrder(): Int = index

        override fun layoutId(): Int = R.layout.item_iot_foot

        override fun show(): Boolean = index != -1
    }
}
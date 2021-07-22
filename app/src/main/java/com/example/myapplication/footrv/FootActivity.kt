package com.example.myapplication.footrv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.bean.User
import com.example.myapplication.databinding.ActivityFootBinding

/**
 *   @author yuechou.zhang
 *   @since  2021/7/20
 */
/**
 * Created by yuechou.zhang
 * on 2021/7/20
 */
class FootActivity : AppCompatActivity() {

    lateinit var binding: ActivityFootBinding
    var dataList: ArrayList<User> = arrayListOf(
        User("张飞", 26),
        User("刘备", 30),
        User("关羽", 28),
        User("黄忠", 30),
        User("马超", 18),
        User("徐晃", 22),
        User("司马懿", 30),
        User("董卓", 34),
        User("孙尚香", 28),
        User("小乔", 30),
        User("颜良", 18),
        User("文丑", 22),
        User("凤雏", 30),
        User("诸葛亮", 34),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foot)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_foot)
        dataList[4].check = true
        dataList[8].check = true
        val selectedUserList = mutableListOf(
            dataList[4],
            dataList[8]
        )

        val recyclerView = binding.rvFoot
        val stickyContainer = binding.stickyContainer
        recyclerView.layoutManager = LinearLayoutManager(this)
        val stickyViewHolders = ArrayList<StickyViewHolder>()
        val footRvDecoration = FootRvDecorationThird(stickyContainer, stickyViewHolders)
        recyclerView.adapter = FootRvAdapter(dataList, footRvDecoration, stickyViewHolders, selectedUserList, this)
        recyclerView.addItemDecoration(footRvDecoration)

    }


}
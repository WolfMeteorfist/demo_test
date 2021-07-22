package com.example.myapplication.mvvm

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yuechou.zhang
 * @since 2021-07-20
 */
open class BaseViewHolderKt<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected val binding = DataBindingUtil.bind<B>(itemView)

}
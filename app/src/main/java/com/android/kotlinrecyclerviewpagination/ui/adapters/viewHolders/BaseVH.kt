package com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

open class BaseVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context

    protected fun getContext(): Context {
        return context
    }
}
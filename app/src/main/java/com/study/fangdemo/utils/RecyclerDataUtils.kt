package com.study.fangdemo.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.fangdemo.R

/**
 *@author zwl
 *@date on 2022/5/27
 */
object RecyclerDataUtils {

    private fun getListString(tag: String?, size: Int): List<String>? {
        val list: MutableList<String> = ArrayList()
        for (i in 0 until size) {
            list.add(StringBuilder(tag).append(":").append(i).toString())
        }
        return list
    }


    fun setRecyclerAdater(context: Context?, recycler: RecyclerView, tag: String?, size: Int) {
        val linearLayoutManager = LinearLayoutManager(context)
        recycler.layoutManager = linearLayoutManager
//        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        recycler.addItemDecoration(dividerItemDecoration)
        val stringAdapter = StringAdapter(getListString(tag, size))
        recycler.adapter = stringAdapter
    }

    private class StringAdapter(private val data: List<String>?) : RecyclerView.Adapter<StringAdapter.VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return VH(inflate)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.itemTv.text = data!![position]
        }

        override fun getItemCount(): Int {
            return data?.size ?: 0
        }

        internal class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemTv: TextView

            init {
                itemTv = itemView.findViewById(R.id.item_tv)
            }
        }
    }
}
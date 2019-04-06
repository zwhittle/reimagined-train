package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.utils.inflate
import kotlinx.android.synthetic.main.list_item_jhtagview.view.*

class JHTagAdapter(private val tags: ArrayList<String>) : RecyclerView.Adapter<JHTagAdapter.JHTagViewHolder>() {

    companion object {
        const val LOG_TAG = "JHTagAdapter"
        const val TAG_TEXT = "tag-text"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JHTagViewHolder {
        val inflatedView = parent.inflate(R.layout.list_item_jhtagview, false)
        return JHTagViewHolder(inflatedView)
    }

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: JHTagViewHolder, position: Int) {
        holder.bindTag(tags[position])
    }


    class JHTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        private var tag: String? = null

        fun bindTag(tag: String) {
            itemView.jhtagview.text = tag
        }
    }
}
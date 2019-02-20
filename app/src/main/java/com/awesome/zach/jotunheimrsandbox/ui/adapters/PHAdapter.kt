package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import com.awesome.zach.jotunheimrsandbox.utils.inflate
import kotlinx.android.synthetic.main.list_item_tag.view.*

class PHAdapter(private val tags: List<Tag>) : RecyclerView.Adapter<PHAdapter.PHViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PHAdapter.PHViewHolder {
        val inflatedView = parent.inflate(R.layout.list_item_tag, false)
        return PHViewHolder(inflatedView)
    }

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: PHAdapter.PHViewHolder,
                                  position: Int) {
        val tag = tags[position]
        holder.bind(tag)
    }

    class PHViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(tag: Tag) = with(view) {
            tvListItemId.text = tag.tagId.toString()
            tvListItemId.setTextColor(Color.parseColor(Utils.inverseHex(tag.colorHex)))
            tvListItemLabel.text = tag.name
            tvListItemLabel.setTextColor(Color.parseColor(Utils.inverseHex(tag.colorHex)))
            view.setBackgroundColor(Color.parseColor(tag.colorHex))
        }
    }
}
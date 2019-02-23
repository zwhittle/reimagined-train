package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Color

class ColorSpinnerAdapter(context: Context, private val resource: Int, private val colors: ArrayList<Color>) :
    ArrayAdapter<Color>(context, resource, colors) {

    companion object {
        const val LOG_TAG = "ColorSpinnerAdapter"
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val color = colors[position]

        val layoutInflater = context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(resource, parent, false)

        val ivColorDot = view.findViewById<ImageView>(R.id.ivSpinnerItemColorDot)
        val tvColorName = view.findViewById<TextView>(R.id.tvSpinnerItemColorName)

        ivColorDot.setColorFilter(android.graphics.Color.parseColor(color.hex))
        tvColorName.text = color.name

        return view
    }
}
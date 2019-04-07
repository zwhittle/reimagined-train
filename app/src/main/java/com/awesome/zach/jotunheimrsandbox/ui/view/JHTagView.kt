package com.awesome.zach.jotunheimrsandbox.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.TextView
import com.awesome.zach.jotunheimrsandbox.R

class JHTagView(context: Context, attrs: AttributeSet): TextView(context, attrs) {

    companion object {
        private const val LOG_TAG = "JHTagView"
        private const val DEFAULT_TAG_COLOR = Color.RED
    }

    // Set up default values of the XML attribute properties, in case you do not pass one of them
    private var color = DEFAULT_TAG_COLOR

    // Paint object for coloring and styling
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        setUpAttributes(attrs)
    }

    private fun setUpAttributes(attrs: AttributeSet) {
        // Object a typed array of the XML attributes
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.JHTagView, 0, 0)

        // Extract custom attributes into member variables
        color = typedArray.getColor(R.styleable.JHTagView_color, DEFAULT_TAG_COLOR)

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        super.onDraw(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        val backgroundRect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(backgroundRect, 0f, 0f, paint)
    }


    fun setColor(value: Int) {
        color = value
    }
}
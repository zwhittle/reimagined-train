package com.awesome.zach.jotunheimrsandbox.data

import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL

class Request(val url: URL) {

    fun run() : Drawable {
        val inputStream: InputStream = URL(url.toString()).content as InputStream
        return Drawable.createFromStream(inputStream, "src name")
    }
}
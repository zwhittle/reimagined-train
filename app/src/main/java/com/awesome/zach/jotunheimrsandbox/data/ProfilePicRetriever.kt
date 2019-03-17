package com.awesome.zach.jotunheimrsandbox.data

import android.graphics.drawable.Drawable
import com.awesome.zach.jotunheimrsandbox.data.api.GoogleProfilePicService
import retrofit2.Callback
import retrofit2.Retrofit

class ProfilePicRetriever {
    private val service: GoogleProfilePicService

    init {
        val retrofit = Retrofit.Builder()
            .build()
        service = retrofit.create(GoogleProfilePicService::class.java)
    }

    fun getProfilePic(callback: Callback<Drawable>) {
        service.retrieveProfilePic().enqueue(callback)
    }

}

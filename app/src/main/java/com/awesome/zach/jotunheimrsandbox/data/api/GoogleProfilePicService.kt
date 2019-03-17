package com.awesome.zach.jotunheimrsandbox.data.api

import android.graphics.drawable.Drawable
import retrofit2.Call
import retrofit2.http.GET


interface GoogleProfilePicService {
    @GET("/profilepic")
    fun retrieveProfilePic() : Call<Drawable>
}
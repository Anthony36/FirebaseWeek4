package com.example.firebaseanthonyr_api25

import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.database.core.Context
import com.google.firebase.storage.StorageReference
import java.io.InputStream

//Firebase:
//https://console.firebase.google.com/u/0/?_gl=1*1whk2ef*_ga*MjE5OTYwMDM5LjE2OTM5MzYxMzQ.*_ga_CW55HF8NVT*MTY5NTY4MzIzNi4xNS4xLjE2OTU2ODMzMjguMC4wLjA.
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(
        context: android.content.Context,
        glide: Glide,
        registry: Registry
    ) {
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
            //will allow gs://fir-anthonyr.appspot.com/Me.png to work
        )
    }
}

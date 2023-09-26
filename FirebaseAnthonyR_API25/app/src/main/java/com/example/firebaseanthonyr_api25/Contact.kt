package com.example.firebaseanthonyr_api25

import android.graphics.drawable.Drawable
import com.google.firebase.database.Exclude

data class Contact(var email: String="", var first_name: String="", var imageLocation: String=""){
    private var _key: String = ""
    var key : String
        @Exclude
        get() {
            return _key
        }
        set(value) {
            _key = value
        }
}

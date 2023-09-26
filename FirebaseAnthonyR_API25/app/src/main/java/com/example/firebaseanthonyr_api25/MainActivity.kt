package com.example.firebaseanthonyr_api25

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import com.example.firebaseanthonyr_api25.GlideApp
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//good ver, as of Sept 26, 2023

        val database = Firebase.database
        val myRef = database.getReference("FirebaseAnthonyR")

        /*
        Caching was on the device, this code had no affect:
        val objMyImgClear:Runnable = Runnable() {
           fun run() {
                GlideApp.get(this).clearDiskCache()
           }
        }
        val cga: Thread = Thread(objMyImgClear)
        cga.start()//start clear
        cga.join()//wait for clear to finish, we want the latest images on our app!
*/

        database.reference.child("contacts").removeValue()

        var contact: Contact = Contact("arunstedler@conestogac.on.ca","Anthony","gs://fir-anthonyr.appspot.com/Me.png")
        database.reference.child("contacts").child(
            UUID.randomUUID().toString()
        ).setValue(contact)
        contact = Contact("someemail@conestogac.on.ca","Someone","https://expertphotography.b-cdn.net/wp-content/uploads/2022/03/Portrait-Photographers-Manny-Librodo-Girl.jpg")
        database.reference.child("contacts").child(
            UUID.randomUUID().toString()
        ).setValue(contact)
        contact = Contact("anotherperson@conestogac.on.ca","Another","data:image/jpeg;base64,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")
        database.reference.child("contacts").child(
            UUID.randomUUID().toString()
        ).setValue(contact)

        Log.d("FirebaseAnthonyR","test")

        val dataList = ArrayList<Contact>()
        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database.reference.child("contacts").get().addOnSuccessListener {
            Log.i("FirebaseAnthonyR","value was:${it.value}")
            val dataSnapshot: DataSnapshot = it

            dataList.clear()
            for (snapshot in dataSnapshot.children){
                val key = snapshot.key.toString() // not currently used, but necessary if we're going to update.
                val first_name = snapshot.child("first_name").value.toString()
                val email = snapshot.child("email").value.toString()
                val imageLocation = snapshot.child("imageLocation").value.toString()
                val contact : Contact = Contact(email, first_name, imageLocation)
                contact.key = key
                dataList.add(contact)
            }
            val adapter = ContactsAdapter(dataList)
            recyclerView.adapter = adapter
        }
        Log.i("FirebaseAnthonyR","after event hookup")
        val b: Bitmap

   }
}
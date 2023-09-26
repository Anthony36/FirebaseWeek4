package com.example.firebaseanthonyr_api25

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.firebaseanthonyr_api25.GlideApp.with
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class ContactsAdapter (private val dataList: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewEmail: TextView = itemView.findViewById(R.id.txtEmail)
        val txtViewFirstName: TextView = itemView.findViewById(R.id.txtFirstName)
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhoto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    //https://guides.codepath.com/android/Working-with-the-ImageView
    //https://bumptech.github.io/glide/doc/getting-started.html
    //https://egemenhamutcu.medium.com/displaying-images-from-firebase-storage-using-glide-for-kotlin-projects-3e4950f6c103
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.txtViewEmail.text = data.email
        holder.txtViewFirstName.text = data.first_name

        //v1, works but not with firebase urls:
        /*
        Glide.with(holder.imgPhoto.context)
            .load(data.imageLocation)
            .into(holder.imgPhoto)
*/

        //v3, loads from firebase storage type urls and normal ones
        if (data.imageLocation.indexOf("gs://")>-1){
            var storage : FirebaseStorage = FirebaseStorage.getInstance()
            var storageReference : StorageReference = storage.getReferenceFromUrl(data.imageLocation)

            GlideApp.with(holder.imgPhoto).load(storageReference).into(holder.imgPhoto);
        }
        else {
            with(holder.imgPhoto).load(data.imageLocation).into(holder.imgPhoto);
        }

        //v2, doesn't work as is, only works on bitmaps
        //val theBitmap: Bitmap = BitmapFactory.decodeFile(data.imageLocation)
        //holder.imgPhoto.setImageBitmap(theBitmap)

    }

    override fun getItemCount(): Int {
        return  dataList.size
    }
}
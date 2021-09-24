package com.example.dogbreed

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.rxdownloader.RxDownloader
import com.squareup.picasso.Picasso

class rvAdapterimage(var context: Context, var list:List<model>) : RecyclerView.Adapter<rvAdapterimage.Myviewholder>() {
    val PERMISSIONS_REQUEST = 1354
    class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img:ImageView=itemView.findViewById(R.id.img)
        val btn:Button=itemView.findViewById(R.id.downloadbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        return Myviewholder(LayoutInflater.from(context).inflate(R.layout.image,parent,false))
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        Picasso.get().load(list[position].imageurl).into(holder.img)
        holder.btn.setOnClickListener{
            downloadImage(list[position].imageurl)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    @SuppressLint("CheckResult")
    private fun downloadImage(photo: String) {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST)
            return
        }
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
        RxDownloader(context)
            .download(photo, Math.random().toString()+".jpg", true) // url, filename, and mimeType
            .subscribe({ path ->
                Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
            }, { throwable ->
                Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }

}
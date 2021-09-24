package com.example.dogbreed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class rvAdapter(var context: Context,var list:List<model>) : RecyclerView.Adapter<rvAdapter.Myviewholder>() {

    class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn:Button=itemView.findViewById(R.id.btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        return Myviewholder(LayoutInflater.from(context).inflate(R.layout.button,parent,false))
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.btn.text=list[position].breed_name
        holder.btn.setOnClickListener{
            var intent=Intent(context,BreedDetail::class.java)
            intent.putExtra("breed",list[position].breed_name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}
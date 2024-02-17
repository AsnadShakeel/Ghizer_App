package com.ese12.gilgitapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ese12.gilgitapp.domain.models.MobilesModel
import com.ese12.gilgitapp.selectcategory.CarsSuvs

class PetsAdapter(var context: Context, private val list: List<PetsModel>) :
    RecyclerView.Adapter<PetsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.sample_house_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.imageUri).into(holder.imageView)
        holder.model.text = data.title
        holder.price.text = "PKR "+data.price
        holder.location.text = data.location
//        holder.date.text = data.date


        holder.itemView.setOnClickListener {
            val intent = Intent(context,PetsDetails::class.java)
            intent.putExtra("list",data)
            context.startActivity(intent)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.img2)
        val price = itemView.findViewById<TextView>(R.id.price)
        val date = itemView.findViewById<TextView>(R.id.tv23)
        val model = itemView.findViewById<TextView>(R.id.tv24)
        val location = itemView.findViewById<TextView>(R.id.tv25)
    }

}
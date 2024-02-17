package com.ese12.gilgitapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ese12.gilgitapp.Activities.MakeAnOffer
import com.ese12.gilgitapp.R
import com.ese12.gilgitapp.domain.models.BuyerRequestModel

class BuyerRequestsAdapter(var context: Context, private val list: List<BuyerRequestModel>) :
    RecyclerView.Adapter<BuyerRequestsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.buyer_requests_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.profileImage).into(holder.profileImage)
        holder.userName.text = data.userName
        holder.price.text = "PKR "+data.price
        holder.location.text = data.location
        holder.lookingFor.text = data.looking_item
        holder.category.text = data.category

        holder.makeAnOffer.setOnClickListener {
            val intent = Intent(context, MakeAnOffer::class.java)
            intent.putExtra("uid",data.uid)
            context.startActivity(intent)
        }

//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context,BuyerDetails::class.java)
//            intent.putExtra("list",data)
//            context.startActivity(intent)
//        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage = itemView.findViewById<ImageView>(R.id.buyerProfileImage)
        val price = itemView.findViewById<TextView>(R.id.price)
        val userName = itemView.findViewById<TextView>(R.id.userName)
        val lookingFor = itemView.findViewById<TextView>(R.id.lookingFor)
        val location = itemView.findViewById<TextView>(R.id.location)
        val category = itemView.findViewById<TextView>(R.id.category)
        val makeAnOffer = itemView.findViewById<TextView>(R.id.makeAnOffer)

    }

}
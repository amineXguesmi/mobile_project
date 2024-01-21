package com.example.mobile_project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_project.R
import com.example.mobile_project.core.models.Product


class CartListAdapter(var productsList: List<Product>,private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    interface ItemClickListener {
        fun onAddClick(product: Product)
        fun onMinusClick(product: Product)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.pic)
        val price: TextView = itemView.findViewById(R.id.feeEachItem)
        val name: TextView = itemView.findViewById(R.id.title)
        val product: ConstraintLayout = itemView.findViewById(R.id.cart_item_layout)
        var quantity: Int = 0
        val add: TextView = itemView.findViewById(R.id.add)
        val minus: TextView = itemView.findViewById(R.id.minus)
        val quantityText: TextView = itemView.findViewById(R.id.textView15)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = productsList[position]
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.price}"
        holder.quantityText.text="0"
        holder.add.setOnClickListener {
            itemClickListener.onAddClick(currentItem)
            holder.quantity++
            holder.quantityText.text = holder.quantity.toString()
        }

        holder.minus.setOnClickListener {
            if (holder.quantity > 1) {
                itemClickListener.onMinusClick(currentItem)
                holder.quantity--
                holder.quantityText.text = holder.quantity.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
    fun updateList(newList: List<Product>) {
        productsList = newList
        notifyDataSetChanged()
    }
}
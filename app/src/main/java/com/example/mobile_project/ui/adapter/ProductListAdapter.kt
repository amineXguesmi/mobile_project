package com.example.mobile_project.ui.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_project.R
import com.example.mobile_project.core.models.Product;


//class ProductListAdapter(var productsList: List<Product> , val onItemClick: (Product) -> Unit){
//}


class ProductListAdapter(var productsList: List<Product>, private val onProductItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val price: TextView = itemView.findViewById(R.id.price)
        val name: TextView = itemView.findViewById(R.id.name)
        val product: ConstraintLayout = itemView.findViewById(R.id.product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = productsList[position]
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.price}"
        holder.product.setOnClickListener {
            onProductItemClick(currentItem.id)
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

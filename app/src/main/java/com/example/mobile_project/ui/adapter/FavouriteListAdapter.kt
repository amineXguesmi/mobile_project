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

class FavouriteListAdapter(private var favouriteList: List<Product>, private var searchString: String, private val onFavouriteClick: (Product) -> Unit, val onItemClick: (Product) -> Unit) :
    RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val price: TextView = itemView.findViewById(R.id.price)
        val name: TextView = itemView.findViewById(R.id.name)
        val product: ConstraintLayout = itemView.findViewById(R.id.product)
        val isFavourite: ImageView = itemView.findViewById(R.id.isFavourite)
    }

    private var filteredProducts : List<Product> = favouriteList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filteredProducts[position]
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)
        holder.name.text = currentItem.name
        "$ ${currentItem.price}".also { holder.price.text = it }
        holder.isFavourite.setImageResource(R.drawable.baseline_favorite_24)
        holder.isFavourite.setOnClickListener { onFavouriteClick(currentItem) }
        holder.product.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return filteredProducts.size
    }
    fun updateList(newList: List<Product>) {
        favouriteList = newList
        filteredProducts = favouriteList.filter { p ->
            searchString.all { char -> p.name.uppercase().contains(char.uppercaseChar()) }
        }
        notifyDataSetChanged()
    }

    fun updateSearchFilter(search : String) {
        try {
            searchString = search
            filteredProducts = favouriteList.filter { p ->
                searchString.all { char -> p.name.uppercase().contains(char.uppercaseChar()) }
            }
            notifyDataSetChanged()
        } catch (e : Error) {
            println(e)
        }
    }
}
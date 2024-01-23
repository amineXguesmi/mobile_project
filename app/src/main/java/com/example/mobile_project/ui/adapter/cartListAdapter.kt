package com.example.mobile_project.ui.adapter

import android.content.Context
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
import com.example.mobile_project.core.models.ProductsCart
import com.example.mobile_project.core.viewmodels.ProductVM


class CartListAdapter(private var productsList: List<ProductsCart>, private val itemClickListener: ItemClickListener, private val productViewModel: ProductVM, private val context: Context) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    interface ItemClickListener {
        fun onAddClick(product: Product)
        fun onMinusClick(product: Product,delete:Boolean)
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
        val deleteButton: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = productsList[position]
        Glide.with(holder.itemView.context).load(currentItem.products.image).into(holder.image)
        holder.name.text = currentItem.products.name
        holder.price.text = "$ ${currentItem.products.price}"
        holder.quantityText.text=currentItem.quantity.toString()
        holder.quantity=currentItem.quantity
        holder.add.setOnClickListener {
            itemClickListener.onAddClick(currentItem.products)
            holder.quantity++
            holder.quantityText.text = holder.quantity.toString()
            productViewModel.updateProductQuantityInCart(context,currentItem.products,holder.quantity)
        }
        holder.deleteButton.setOnClickListener {
            while(holder.quantity>0){
                itemClickListener.onMinusClick(currentItem.products,true)
                holder.quantity--
            }
            holder.quantityText.text = holder.quantity.toString()
            productViewModel.deleteProductFromCart(context,currentItem.products)
        }
        holder.minus.setOnClickListener {
            if (holder.quantity > 1) {
                itemClickListener.onMinusClick(currentItem.products,false)
                holder.quantity--
                holder.quantityText.text = holder.quantity.toString()
                productViewModel.updateProductQuantityInCart(context,currentItem.products,holder.quantity)
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
    fun updateList(newList: List<ProductsCart>) {
        productsList = newList
        notifyDataSetChanged()
    }
}
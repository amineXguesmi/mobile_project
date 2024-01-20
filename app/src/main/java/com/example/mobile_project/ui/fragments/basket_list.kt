package com.example.mobile_project.ui.fragments

import ProductService
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.core.models.ProductsData
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.ui.adapter.BasketOrdersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class basket_list : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BasketOrdersAdapter
    private var products: List<Product> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_basket_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewBasketOrders)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = BasketOrdersAdapter(listOf()) // Initialize with empty list
        recyclerView.adapter = adapter

        loadProducts()

        return view
    }

    private fun loadProducts() {
        val productService = ProductService()
        productService.getProducts().enqueue(object : Callback<ProductsData> {
            override fun onResponse(call: Call<ProductsData>, response: Response<ProductsData>) {
                if (response.isSuccessful && response.body() != null) {
                    products = response.body()!!.products
                    adapter = BasketOrdersAdapter(products)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ProductsData>, t: Throwable) {
                activity?.runOnUiThread {
                    AlertDialog.Builder(activity)
                        .setTitle("Connection Error")
                        .setMessage("There was a problem connecting to the network. Please check your internet connection and try again.")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }

        })
    }

    companion object {
    }
}

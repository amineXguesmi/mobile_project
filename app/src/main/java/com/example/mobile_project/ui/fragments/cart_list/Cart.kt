package com.example.mobile_project.ui.fragments.cart_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentCartBinding
import com.example.mobile_project.ui.adapter.CartListAdapter
import com.example.mobile_project.ui.adapter.ProductListAdapter


class Cart : Fragment() ,CartListAdapter.ItemClickListener{

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductVM by activityViewModels()
    private lateinit var adapter: CartListAdapter
    var totalPrice = 0.0

    private lateinit var totalTax: TextView
    private lateinit var totalFreeTax: TextView
    private lateinit var DeliveryTxt: TextView
    private lateinit var totalAmount: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        totalTax= binding.totalTax
        totalFreeTax= binding.TotalFreeTax
        DeliveryTxt= binding.DeliveryTxt
        totalAmount= binding.Total
        val productListRecyclerView: RecyclerView = binding.cartView
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CartListAdapter(emptyList(),this)
        productListRecyclerView.adapter = adapter
        productViewModel.cartProduct.observe(viewLifecycleOwner) { products ->
            adapter.updateList(products)
        }
        return binding.root
    }

    override fun onAddClick(product: Product) {
        totalPrice += product.price
        totalTax.text = (totalPrice/10).toString()
        val totalTaxValue = if (totalTax.text.isNotEmpty()) totalTax.text.toString().toDouble() else 0.0
        totalFreeTax.text = totalPrice.toString()
        DeliveryTxt.text = "10.0"
        totalAmount.text = (totalPrice + 10.0 + totalTaxValue).toString()
    }


    override fun onMinusClick(product: Product) {
        totalPrice -= product.price

        val totalTaxValue = totalPrice / 10
        totalTax.text = totalTaxValue.toString()
        totalFreeTax.text = totalPrice.toString()
        DeliveryTxt.text = "10.0"
        totalAmount.text = (totalPrice + 10.0 + totalTaxValue).toString()
    }
}
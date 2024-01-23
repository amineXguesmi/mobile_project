package com.example.mobile_project.ui.fragments.cart_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductsCart
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentCartBinding
import com.example.mobile_project.ui.adapter.CartListAdapter
import com.example.mobile_project.ui.shared.DialogUtils


class Cart : Fragment() ,CartListAdapter.ItemClickListener{

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductVM by activityViewModels()
    private lateinit var adapter: CartListAdapter
    private var totalPrice = 0.0

    private lateinit var totalTax: TextView
    private lateinit var totalFreeTax: TextView
    private lateinit var deliveryTxt: TextView
    private lateinit var totalAmount: TextView
    private lateinit var buyButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentCartBinding.inflate(layoutInflater)
        productViewModel.getCartProducts(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        totalTax= binding.totalTax
        totalFreeTax= binding.TotalFreeTax
        deliveryTxt= binding.DeliveryTxt
        totalAmount= binding.Total
        buyButton= binding.Buy
        val productListRecyclerView: RecyclerView = binding.cartView
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CartListAdapter(emptyList(),this,productViewModel,requireContext())
        productListRecyclerView.adapter = adapter

        productViewModel.cartProduct.observe(viewLifecycleOwner) { products ->
            adapter.updateList(products)
            calculateTotals(products)
        }

        productViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                DialogUtils.showErrorDialog(requireContext(), it)
            }
        }

        buyButton.setOnClickListener{
           val total:String= totalAmount.text.toString()+" $"
            DialogUtils.shoCustomDialog(requireContext(),total,productViewModel)
        }
        return binding.root
    }



    override fun onAddClick(product: Product) {
        totalPrice += product.price
        totalTax.text = (totalPrice/10).toString()
        val totalTaxValue = if (totalTax.text.isNotEmpty()) totalTax.text.toString().toDouble() else 0.0
        totalFreeTax.text = totalPrice.toString()
        "10.0".also { deliveryTxt.text = it }
        (totalPrice + 10.0 + totalTaxValue).toString().also { totalAmount.text = it }
    }


    override fun onMinusClick(product: Product,delete:Boolean) {
        totalPrice -= product.price
        val totalTaxValue = totalPrice / 10
        totalTax.text = totalTaxValue.toString()
        totalFreeTax.text = totalPrice.toString()
        deliveryTxt.text = if(delete) "0.0" else "10.0"
        (totalPrice + deliveryTxt.text.toString().toDouble() + totalTaxValue).toString()
            .also { totalAmount.text = it }
    }

    private fun calculateTotals(products: List<ProductsCart>) {
        totalPrice = 0.0
        for (product in products) {
            for (i in 1..product.quantity)
                totalPrice += product.products.price
        }
        totalTax.text = (totalPrice / 10).toString()
        val totalTaxValue = if (totalTax.text.isNotEmpty()) totalTax.text.toString().toDouble() else 0.0
        totalFreeTax.text = totalPrice.toString()
        deliveryTxt.text = if (products.isNotEmpty()) "10.0" else "0.0"
        (totalPrice + deliveryTxt.text.toString().toDouble() + totalTaxValue).toString()
            .also { totalAmount.text = it }
    }
}
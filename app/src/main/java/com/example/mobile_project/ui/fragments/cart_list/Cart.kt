package com.example.mobile_project.ui.fragments.cart_list

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductsCart
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentCartBinding
import com.example.mobile_project.ui.adapter.CartListAdapter


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
        DeliveryTxt= binding.DeliveryTxt
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

        buyButton.setOnClickListener{
           val total:String= totalAmount.text.toString()+" $"
            shoCustomDialog(total)
        }
        return binding.root
    }

    private fun shoCustomDialog(total:String){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(com.example.mobile_project.R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val price:TextView = dialog.findViewById(com.example.mobile_project.R.id.price)
        val okButton:Button = dialog.findViewById(com.example.mobile_project.R.id.buttonConfirm)
        val cancelButton:Button = dialog.findViewById(com.example.mobile_project.R.id.buttonCancel)
        price.text=total
        okButton.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"Your order has been confirmed",Toast.LENGTH_SHORT).show()
            productViewModel.deleteAllProductsFromCart(requireContext())
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onAddClick(product: Product) {
        totalPrice += product.price
        totalTax.text = (totalPrice/10).toString()
        val totalTaxValue = if (totalTax.text.isNotEmpty()) totalTax.text.toString().toDouble() else 0.0
        totalFreeTax.text = totalPrice.toString()
        DeliveryTxt.text = "10.0"
        totalAmount.text = (totalPrice + 10.0 + totalTaxValue).toString()
    }


    override fun onMinusClick(product: Product,delete:Boolean) {
        totalPrice -= product.price
        val totalTaxValue = totalPrice / 10
        totalTax.text = totalTaxValue.toString()
        totalFreeTax.text = totalPrice.toString()
        DeliveryTxt.text = if(delete) "0.0" else "10.0"
        totalAmount.text = (totalPrice + DeliveryTxt.text.toString().toDouble() + totalTaxValue).toString()
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
        DeliveryTxt.text = if (products.isNotEmpty()) "10.0" else "0.0"
        totalAmount.text = (totalPrice + DeliveryTxt.text.toString().toDouble() + totalTaxValue).toString()
    }
}
package com.example.mobile_project.ui.fragments.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentProductDetailsBinding
import com.example.mobile_project.databinding.FragmentProductListBinding
import com.example.mobile_project.databinding.FragmentPruductDetailBinding


class ProductDetail : Fragment() {
    private val productViewModel: ProductVM by activityViewModels()
    private var _binding: FragmentPruductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var productName:TextView
    private lateinit var productPrice:TextView
    private lateinit var productDescription:TextView
    private lateinit var productImage:ImageView
    private lateinit var productStock:TextView
    private lateinit var addCartButton:Button
    private lateinit var returnTextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentPruductDetailBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productId = arguments?.getString("product_id")
        productViewModel.getProductById(productId!!)
        productName=binding.nameTextView
        productPrice=binding.priceTextView
        productDescription=binding.descriptionTextView
        productImage=binding.imageViewProduct
        productStock=binding.textViewStock
        addCartButton=binding.buttonAddToCart
        returnTextView=binding.returnTextView
        returnTextView.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_pruduct_detail_to_productList,
            )
        }
        productViewModel.product.observe(viewLifecycleOwner) { product ->
            productName.text=product.name
            productPrice.text="Product Price: ${product.price}"
            productDescription.text=product.description
            if(product.stock==0){
                productStock.text="Out of stock"}
            else
            {
                productStock.text="Stock : ${product.stock.toString()}"
            }
            Glide.with(this).load(product.image).into(productImage)
            addCartButton.setOnClickListener {
                productViewModel.addProductToCart(requireContext(),product)
                view?.findNavController()?.navigate(
                    R.id.action_pruduct_detail_to_productList,
                )
            }
        }
        return binding.root
    }


}
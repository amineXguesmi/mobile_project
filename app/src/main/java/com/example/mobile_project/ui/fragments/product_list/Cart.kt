package com.example.mobile_project.ui.fragments.product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.databinding.FragmentCartBinding
import com.example.mobile_project.databinding.FragmentProductListBinding
import com.example.mobile_project.ui.adapter.ProductListAdapter


class Cart : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductVM by activityViewModels()

    fun toggleFavourite(product : Product, isFavourtite : Boolean) {
        if(isFavourtite) {
            productViewModel.deleteProductFromFavourite(requireContext() , product)
        } else {
            productViewModel.addProductToFavourite(requireContext() , product)
        }
    }

    fun redirectToProductDetails(product : Product) : Unit {
        view?.findNavController()?.navigate(
            R.id.action_productList_to_pruduct_detail,
            Bundle().apply {
                putString("product_id", product.id)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val productListRecyclerView: RecyclerView = binding.cartView
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ProductListAdapter(emptyList() , emptyList() , ::toggleFavourite , ::redirectToProductDetails)
        productListRecyclerView.adapter = adapter
        productViewModel.cartProduct.observe(viewLifecycleOwner) { products ->
            adapter.updateList(products)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
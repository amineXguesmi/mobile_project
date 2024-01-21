package com.example.mobile_project.ui.fragments.product

import android.os.Bundle
import android.provider.Settings.Global.putString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.core.viewmodels.ProductVMFactory
import com.example.mobile_project.core.viewmodels.UserVm
import com.example.mobile_project.databinding.FragmentProductListBinding
import com.example.mobile_project.ui.adapter.ProductListAdapter

class ProductList : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel:ProductVM by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentProductListBinding.inflate(layoutInflater)


//        val productsObserver : Observer<List<Product>> = Observer<List<Product>> { products : List<Product> -> adapter.updateList(products)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productListRecyclerView : RecyclerView = binding.productListRecyclerView
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ProductListAdapter(emptyList()) { productId ->
            view?.findNavController()?.navigate(
                R.id.action_productList_to_pruduct_detail,
                Bundle().apply {
                    putString("product_id", productId)
                }
            )
        }
        productListRecyclerView.adapter = adapter
        productViewModel.products.observe(viewLifecycleOwner) {products -> adapter.updateList(products)}
        return binding.root
    }

}
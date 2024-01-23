package com.example.mobile_project.ui.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.R
import com.example.mobile_project.core.models.Categories
import com.example.mobile_project.core.models.Category
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentProductListBinding
import com.example.mobile_project.ui.adapter.ProductListAdapter
import com.example.mobile_project.ui.adapter.UpdateType
import com.example.mobile_project.ui.shared.DialogUtils

class ProductList : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel:ProductVM by activityViewModels()
    private lateinit var searchProductInput : EditText
    private var searchString : String = ""
    private var categories : List<Category> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentProductListBinding.inflate(layoutInflater)
        productViewModel.getFavouriteProducts(requireContext())
        productViewModel.getCategories()

    }

    private fun toggleFavourite(product : Product, isFavourite : Boolean) {
        if(isFavourite) {
            productViewModel.deleteProductFromFavourite(requireContext() , product)
        } else {
            productViewModel.addProductToFavourite(requireContext() , product)
        }
    }

    private fun redirectToProductDetails(product : Product) {
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
        searchProductInput = binding.searchInput
        val productListRecyclerView : RecyclerView = binding.productListRecyclerView
        val spinner : Spinner = binding.spinner
        spinner.adapter = ArrayAdapter<Category>(requireContext() , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item , categories)
        productViewModel.categories.observe(viewLifecycleOwner) {categories = it ; spinner.adapter = ArrayAdapter<Category>(requireContext() , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item , categories)
        }
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ProductListAdapter(emptyList() , emptyList() ,searchString, ::toggleFavourite , ::redirectToProductDetails)
        productListRecyclerView.adapter = adapter
        productViewModel.products.observe(viewLifecycleOwner) {adapter.updateList(it)}
        productViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                DialogUtils.showErrorDialog(requireContext(), it)
            }
        }
        productViewModel.favouriteProduct.observe(viewLifecycleOwner) {adapter.updateList(it , UpdateType.FAV)}
        searchProductInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
               adapter.updateSearchFilter(s.toString())
            }
        })
        return binding.root
    }

}
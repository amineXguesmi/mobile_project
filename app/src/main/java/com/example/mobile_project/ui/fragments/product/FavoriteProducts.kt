package com.example.mobile_project.ui.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.R
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.viewmodels.ProductVM
import com.example.mobile_project.databinding.FragmentProductListBinding
import com.example.mobile_project.ui.adapter.FavouriteListAdapter
import com.example.mobile_project.ui.shared.DialogUtils

class FavoriteProducts : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductVM by activityViewModels()
    private lateinit var searchProductInput : EditText
    private var searchString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentProductListBinding.inflate(layoutInflater)
        productViewModel.getFavouriteProducts(requireContext())
    }

    private fun toggleFavourite(product : Product) {
        productViewModel.deleteProductFromFavourite(requireContext() , product)
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
        val productListRecyclerView : RecyclerView = binding.productListRecyclerView
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)
        productViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                DialogUtils.showErrorDialog(requireContext(), it)
            }
        }
        val adapter = FavouriteListAdapter(emptyList(),searchString,::toggleFavourite , ::redirectToProductDetails)
        productListRecyclerView.adapter = adapter
        productViewModel.favouriteProduct.observe(viewLifecycleOwner) {adapter.updateList(it)}
        searchProductInput = binding.searchInput
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
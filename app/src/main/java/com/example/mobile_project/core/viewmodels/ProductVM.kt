package com.example.mobile_project.core.viewmodels

import ProductService
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_project.core.models.Categories
import com.example.mobile_project.core.models.Category
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductsData
import com.example.mobile_project.core.services.category.CategoryService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductVM(private val productService: ProductService = ProductService() , private val categorService: CategoryService = CategoryService()) : ViewModel() {
    val products : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    var error : MutableLiveData<String> = MutableLiveData<String>()
    val product : MutableLiveData<Product> = MutableLiveData<Product>()
    val cartProduct : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    val favouriteProduct : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    val categories : MutableLiveData<List<Category>> = MutableLiveData<List<Category>>()
    private val PREF_NAME = "MyAppPrefs"
    private val KEY_CART_PRODUCTS = "cartProducts"
    private val KEY_FAVOURITE_PRODUCTS = "favProducts"



    fun getProducts() {

        val call: Call<ProductsData> = productService.getProducts()

        call.enqueue(object : Callback<ProductsData> {
            override fun onResponse(call: Call<ProductsData>, response: Response<ProductsData>) {
                if (response.isSuccessful) {
                    val result: ProductsData? = response.body()
                    if (result != null) {
                        products.postValue(result.products)
                    }
                } else {
                    error.postValue("an error has occured while fetching data")
                }
            }

            override fun onFailure(call: Call<ProductsData>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }

    fun getCategories() {

        val call: Call<List<Category>> = categorService.getCategories()

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val result: List<Category>? = response.body()
                    if (result != null) {
                        categories.postValue(result ?: emptyList())
                    }
                } else {
                    error.postValue("an error has occured while fetching data")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }

    fun getProductById(id: String) {

        val call: Call<Product> = productService.getProductById(id)

        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val result: Product? = response.body()
                    if (result != null) {
                        product.postValue(result!!)
                    }
                } else {
                    error.postValue("an error has occured while fetching data")
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }


    private fun saveCartProducts(context: Context, cartProducts: List<Product>) {
        val gson = Gson()
        val json = gson.toJson(cartProducts)

        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_CART_PRODUCTS, json)
        editor.apply()

    }

    fun getCartProducts(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CART_PRODUCTS, null)

        val gson = Gson()
        val type = object : TypeToken<List<Product>>() {}.type
        val cartProducts = gson.fromJson<List<Product>>(json, type) ?: emptyList()
        cartProduct.postValue(cartProducts)
    }


    fun addProductToCart(context: Context, product: Product) {
        val currentCartProducts = cartProduct.value?.toMutableList() ?: mutableListOf()
        if (!currentCartProducts.contains(product)) {
            currentCartProducts.add(product)
            cartProduct.postValue(currentCartProducts)
            saveCartProducts(context, currentCartProducts)
            getCartProducts(context)
        }
    }

    private fun  saveFavouriteProducts(context: Context, favouriteProducts: List<Product>) {
        val gson = Gson()
        val json = gson.toJson(favouriteProducts)

        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_FAVOURITE_PRODUCTS, json)
        editor.apply()
    }

    fun getFavouriteProducts(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_FAVOURITE_PRODUCTS, null)

        val gson = Gson()
        val type = object : TypeToken<List<Product>>() {}.type
        favouriteProduct.postValue(gson.fromJson(json, type) ?: emptyList())
    }

    fun addProductToFavourite(context: Context, product: Product) {
        val favouriteProduct = favouriteProduct.value?.toMutableList() ?: mutableListOf()
        favouriteProduct.add(product)
        saveFavouriteProducts(context, favouriteProduct)
        getFavouriteProducts(context)
    }
    fun deleteProductFromFavourite(context: Context, product: Product) {
        val favouriteProduct = favouriteProduct.value?.toMutableList() ?: mutableListOf()
        val newFav = favouriteProduct.filter { it.id != product.id }
        saveFavouriteProducts(context, newFav)
        getFavouriteProducts(context)
    }
}
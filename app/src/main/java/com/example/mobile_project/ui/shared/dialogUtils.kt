package com.example.mobile_project.ui.shared

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import com.example.mobile_project.R
import com.example.mobile_project.core.viewmodels.ProductVM

object DialogUtils {
    fun showErrorDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
    fun shoCustomDialog(context: Context,total:String,productViewModel: ProductVM){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val price:TextView = dialog.findViewById(com.example.mobile_project.R.id.price)
        val okButton: Button = dialog.findViewById(R.id.buttonConfirm)
        val cancelButton: Button = dialog.findViewById(R.id.buttonCancel)
        price.text=total
        okButton.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context,"Your order has been confirmed", Toast.LENGTH_SHORT).show()
            productViewModel.deleteAllProductsFromCart(context)
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}
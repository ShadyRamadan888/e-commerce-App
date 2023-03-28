package com.example.e_commerceapp.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.e_commerceapp.R

class LoadingDialog(val mActivity: Activity) {

    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item,null)

        val builder =AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }
}
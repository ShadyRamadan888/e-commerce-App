package com.example.e_commerceapp.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

class FacebookShimmerFactory(var shimmerFrameLayout: ShimmerFrameLayout) {

    fun startShimmer() {
        shimmerFrameLayout.startShimmer()
    }

    fun stopShimmer() {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.visibility = View.GONE
    }

}
package com.example.e_commerceapp.ui.activties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityProductDetailsBinding
import com.example.e_commerceapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }


    }
}
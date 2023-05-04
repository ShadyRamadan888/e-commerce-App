package com.example.e_commerceapp.di

import android.content.Context
import com.example.e_commerceapp.ui.fragment.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepoModule::class,
        UseCaseModule::class,
    ]
)
interface MyComponent {
    fun injectHomeFragment(homeFragment: HomeFragment)
}
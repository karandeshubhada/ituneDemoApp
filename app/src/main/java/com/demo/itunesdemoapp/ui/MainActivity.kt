package com.demo.itunesdemoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.itunesdemoapp.R
import com.demo.itunesdemoapp.databinding.ActivityMainBinding
import com.demo.itunesdemoapp.ui.detail.MovieDetailsFragment
import com.demo.itunesdemoapp.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    val homeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        homeViewModel.openFragment.observe(this, Observer {
            supportFragmentManager.transaction {

                add(R.id.container, MovieDetailsFragment())
                addToBackStack(null)
            }
        })
    }

}

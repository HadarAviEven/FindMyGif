package com.hadar.findmygif.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hadar.findmygif.R
import com.hadar.findmygif.ui.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private var mainActivityViewModel: MainActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMainActivityViewModel()
    }


    private fun initMainActivityViewModel() {
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()

        mainActivityViewModel?.onStart()
    }

    override fun onPause() {
        super.onPause()

        mainActivityViewModel?.onPause()
    }
}
package com.android.fpuy.basearchitecture.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this is ViewModelInterface<*>) {
            setupViewModel(this)
        }
    }

}
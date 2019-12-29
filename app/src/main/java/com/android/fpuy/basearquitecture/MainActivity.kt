package com.android.fpuy.basearquitecture

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import com.android.fpuy.basearchitecture.core.AbstractActivity
import com.android.fpuy.basearchitecture.core.ViewModelInterface
import com.android.fpuy.basearchitecture.core.ViewState
import com.android.fpuy.basearchitecture.core.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractActivity(), ViewModelInterface<MainViewModel> {

    override val viewModel by getViewModel {
        MainViewModel()
    }

    override fun onStateReceived(state: ViewState) {
        when (state) {
            is MainState.ShowText -> showText(state.text)
            is MainState.Loading -> processLoading(state.isLoading)
        }
    }

    private fun processLoading(isLoading: Boolean) {
        spinner.visibility = if (isLoading) VISIBLE else View.GONE

    }

    private fun showText(text: String) {
        textView.text = text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        btShowText.setOnClickListener { viewModel.buttonClicked() }
    }
}

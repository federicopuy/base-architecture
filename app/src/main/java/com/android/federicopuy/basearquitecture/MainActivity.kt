package com.android.federicopuy.basearquitecture

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import com.android.federicopuy.basearchitecture.core.AbstractActivity
import com.android.federicopuy.basearchitecture.core.ViewModelInterface
import com.android.federicopuy.basearchitecture.core.ViewState
import com.android.federicopuy.basearchitecture.core.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractActivity(), ViewModelInterface<MainViewModel> {

    override val viewModel by getViewModel {
        MainViewModel(MainRepository(getNetworkService()))
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

package com.android.fpuy.basearquitecture

import com.android.fpuy.basearchitecture.core.ViewState

sealed class MainState : ViewState {
    data class ShowText(val text: String) : MainState()
    data class Loading(val isLoading: Boolean) : MainState()

}
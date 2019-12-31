package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.core.ViewState

sealed class MainState : ViewState {
    data class ShowText(val text: String) : MainState()
    data class Loading(val isLoading: Boolean) : MainState()
}
package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.core.ViewState

sealed class MainState : ViewState {
    data class ShowCharacter(val text: String) : MainState()
    data class Loading(val isLoading: Boolean) : MainState()
    data class ShowCharacterInfo(val text: String) : MainState()
    object ResetScreen : MainState()

}
package com.android.fpuy.basearquitecture

import com.android.fpuy.basearchitecture.core.AbstractViewModel
import com.android.fpuy.basearchitecture.core.launch
import com.android.fpuy.basearchitecture.core.liveData
import kotlinx.coroutines.delay

class MainViewModel : AbstractViewModel() {

    private val text = liveData<MainState.ShowText>()
    private val spinner = liveData<MainState.Loading>()

    fun buttonClicked() {
        launch {
            updateText()
        }
    }

    suspend fun updateText() {
        spinner.postValue(MainState.Loading(true))
        delay(1000)
        text.postValue(MainState.ShowText("Hello World"))
        spinner.postValue(MainState.Loading(false))
    }
}
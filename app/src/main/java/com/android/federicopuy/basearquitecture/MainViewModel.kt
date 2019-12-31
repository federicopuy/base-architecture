package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.core.AbstractViewModel
import com.android.federicopuy.basearchitecture.core.launch
import com.android.federicopuy.basearchitecture.core.liveData
import com.android.federicopuy.basearchitecture.networking.ApiResult
import com.android.federicopuy.basearquitecture.model.Character

class MainViewModel(private val repository: MainRepository) : AbstractViewModel() {

    var characterId = 1

    private val text = liveData<MainState.ShowText>()
    private val spinner = liveData<MainState.Loading>()

    fun buttonClicked() {
        launch {
            fetchCharacter()
        }
    }

    suspend fun fetchCharacter() {
        spinner.postValue(MainState.Loading(true))

        val response = repository.getNextCharacter(characterId)
        when (response) {
            is ApiResult.Success<Character> -> {
                text.postValue(MainState.ShowText(response.value.name))
                characterId++
            }
            is ApiResult.Error -> {
                text.postValue(MainState.ShowText("Error fetching Character"))
            }
        }
        spinner.postValue(MainState.Loading(false))
    }
}
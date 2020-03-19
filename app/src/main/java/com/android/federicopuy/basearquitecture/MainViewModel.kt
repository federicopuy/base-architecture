package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.core.AbstractViewModel
import com.android.federicopuy.basearchitecture.core.async
import com.android.federicopuy.basearchitecture.core.launch
import com.android.federicopuy.basearchitecture.core.liveData
import com.android.federicopuy.basearchitecture.networking.ApiResult
import com.android.federicopuy.basearquitecture.model.Character
import com.android.federicopuy.basearquitecture.model.Film
import com.android.federicopuy.basearquitecture.model.Species

class MainViewModel(private val repository: MainRepository) : AbstractViewModel() {

    var characterId = 1

    private val characterLiveData = liveData<MainState.ShowCharacter>()
    private val characterInfoLiveData = liveData<MainState.ShowCharacterInfo>()
    private val spinner = liveData<MainState.Loading>()
    private val resetScreen = liveData<MainState.ResetScreen>()

    fun firstButtonClicked() = launch {
        spinner.postValue(MainState.Loading(true))

        val characterName = fetchCharacter()
        characterLiveData.postValue(MainState.ShowCharacter(characterName))

        spinner.postValue(MainState.Loading(false))
    }

    fun secondButtonClicked() = launch {
        spinner.postValue(MainState.Loading(true))

        val species = async { fetchSpecies() }
        val film = async { fetchFilm() }

        characterInfoLiveData.postValue(MainState.ShowCharacterInfo("The character is ${species.await()} in the film: ${film.await()}"))

        spinner.postValue(MainState.Loading(false))
    }

    fun thirdButtonClicked() = resetScreen.postValue(MainState.ResetScreen)

    suspend fun fetchCharacter(): String {
        return when (val response = repository.getNextCharacter(characterId)) {
            is ApiResult.Success<Character> -> {
                response.value.name
            }
            is ApiResult.Error -> {
                "Error fetching Character"
            }
        }
    }

    suspend fun fetchSpecies(): String {
        return when (val response = repository.getSpecies(characterId)) {
            is ApiResult.Success<Species> -> {
                response.value.name
            }
            is ApiResult.Error -> {
                "Error fetching Species"
            }
        }
    }

    suspend fun fetchFilm(): String {
        return when (val response = repository.getFilm(characterId)) {
            is ApiResult.Success<Film> -> {
                response.value.name
            }
            is ApiResult.Error -> {
                "Error fetching Film"
            }
        }
    }
}
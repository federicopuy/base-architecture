package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.networking.ApiResult
import com.android.federicopuy.basearchitecture.networking.suspendApiCallCoroutine
import com.android.federicopuy.basearquitecture.model.Character
import com.android.federicopuy.basearquitecture.model.Film
import com.android.federicopuy.basearquitecture.model.Species

class MainRepository(private val network: MainNetwork) {

    suspend fun getNextCharacter(id: Int): ApiResult<Character> {
        return suspendApiCallCoroutine { network.fetchCharacter(id) }
    }

    suspend fun getSpecies(id: Int): ApiResult<Species> {
        return suspendApiCallCoroutine { network.fetchSpecies(id) }
    }

    suspend fun getFilm(id: Int): ApiResult<Film> {
        return suspendApiCallCoroutine { network.fetchFilm(id) }
    }
}
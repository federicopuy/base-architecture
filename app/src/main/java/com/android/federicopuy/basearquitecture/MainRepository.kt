package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearchitecture.networking.ApiResult
import com.android.federicopuy.basearchitecture.networking.suspendApiCallCoroutine
import com.android.federicopuy.basearquitecture.model.Character

class MainRepository(private val network: MainNetwork) {

    suspend fun getNextCharacter(count: Int): ApiResult<Character> {
        return suspendApiCallCoroutine { network.fetchCharacter(count) }
    }
}
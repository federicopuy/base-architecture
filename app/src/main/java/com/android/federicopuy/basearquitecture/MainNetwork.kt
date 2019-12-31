package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearquitecture.model.Character
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val service: MainNetwork by lazy {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.co/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MainNetwork::class.java)
}

fun getNetworkService() = service


interface MainNetwork {

    /**
     * Service to retrieve Change Pin Screen
     *
     * @param cardId
     * @return the ChangePin model to render the screen
     */
    @GET(" people/{count}")
    suspend fun fetchCharacter(@Path("count") count: Int): Character
}
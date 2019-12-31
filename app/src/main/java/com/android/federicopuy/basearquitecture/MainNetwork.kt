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
     * Service to retrieve Character
     *
     * @param id the character Id
     * @return the Character obtained from the service
     */
    @GET(" people/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): Character
}
package com.android.federicopuy.basearquitecture

import com.android.federicopuy.basearquitecture.model.Character
import com.android.federicopuy.basearquitecture.model.Film
import com.android.federicopuy.basearquitecture.model.Species
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private val service: MainNetwork by lazy {

    val interceptor = HttpLoggingInterceptor()
    interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://5e6578cc2aea440016afb113.mockapi.io/")
        .client(client)
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
    @GET("character/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): Character

    /**
     * Service to retrieve Species
     *
     * @param id the Species Id
     * @return the Species obtained from the service
     */
    @GET("species/{id}")
    suspend fun fetchSpecies(@Path("id") id: Int): Species

    /**
     * Service to retrieve Film
     *
     * @param id the Film Id
     * @return the Film obtained from the service
     */
    @GET("film/{id}")
    suspend fun fetchFilm(@Path("id") id: Int): Film
}
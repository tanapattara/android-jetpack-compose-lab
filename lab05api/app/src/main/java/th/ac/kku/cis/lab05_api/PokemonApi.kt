package th.ac.kku.cis.lab05_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface PokemonApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("pokemon")
    abstract fun getPokemonList() : Call<PokemonList>
}
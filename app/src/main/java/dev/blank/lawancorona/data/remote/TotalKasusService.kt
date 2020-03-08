package dev.blank.lawancorona.data.remote

import dev.blank.lawancorona.data.model.TotalKasus
import retrofit2.Call
import retrofit2.http.GET

interface TotalKasusService {

    @GET("positif")
    fun getTotalKasusPositif(): Call<TotalKasus>

    @GET("sembuh")
    fun getTotalKasusSembuh(): Call<TotalKasus>

    @GET("meninggal")
    fun getTotalKasusMeninggal(): Call<TotalKasus>

}
package dev.blank.lawancorona.data.remote

import dev.blank.lawancorona.data.model.Kasus
import dev.blank.lawancorona.data.model.KasusRaw
import retrofit2.Call
import retrofit2.http.GET

interface KasusService {
    @GET("/")
    fun getKasus(): Call<List<KasusRaw>>

}
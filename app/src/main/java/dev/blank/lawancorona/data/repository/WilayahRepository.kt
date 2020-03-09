package dev.blank.lawancorona.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.blank.lawancorona.R
import dev.blank.lawancorona.data.local.Preferences
import dev.blank.lawancorona.data.model.Kasus
import dev.blank.lawancorona.data.model.KasusRaw
import dev.blank.lawancorona.data.network.RetrofitClient
import dev.blank.lawancorona.data.remote.KasusService
import dev.blank.lawancorona.util.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class WilayahRepository(private var context: Context) {
    var listKasus: MutableLiveData<List<Kasus>> = MutableLiveData<List<Kasus>>()
    var kasusFilter: MutableLiveData<Kasus> = MutableLiveData<Kasus>()


    init {
        getWilayah()
    }

    private fun populateKasus(): Kasus {
        val kasus = Kasus()
        kasus.countryRegion = context.getString(R.string.dunia)
        kasus.id = -1
        kasus.lastUpdate = Converter.getDateTime(System.currentTimeMillis().toString())

        return kasus
    }

    fun filterWilayah() {
        if (Preferences.getWilayah(context) != "ALL") {
            kasusFilter.postValue(listKasus.value!!.single { kasus -> kasus.countryRegion == Preferences.getWilayah(context) })
        }
    }

    private fun getWilayah() {
        val retrofit: Retrofit = RetrofitClient.service
        val getWilayah: Call<List<KasusRaw>>? = retrofit.create(KasusService::class.java).getKasus()

        getWilayah!!.enqueue(object : Callback<List<KasusRaw>> {
            override fun onFailure(call: Call<List<KasusRaw>>, t: Throwable) {
                Log.e("LawanCorona", "Tidak terhubung ke Server")
            }

            override fun onResponse(call: Call<List<KasusRaw>>, response: Response<List<KasusRaw>>) {
                if (response.isSuccessful) {
                    val dataListKasus: MutableList<Kasus> = ArrayList()
                    dataListKasus.add(populateKasus())
                    for (kasus in response.body()!!) {
                        kasus.attributes!!.lastUpdate = Converter.getDateTime(kasus.attributes.lastUpdate!!)
                        dataListKasus.add(kasus.attributes)
                    }
                    listKasus.postValue(dataListKasus)

                    if (Preferences.getWilayah(context) != "ALL") {
                        println("Region : ${Preferences.getWilayah(context)}")
                        kasusFilter.postValue(dataListKasus.single { kasus -> kasus.countryRegion == Preferences.getWilayah(context) })
                    }
                } else {
                    Log.e("LawanCorona", "Tidak terhubung ke Server")
                }
            }

        })
    }
}
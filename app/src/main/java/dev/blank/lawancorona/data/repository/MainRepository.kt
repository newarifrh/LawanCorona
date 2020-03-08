package dev.blank.lawancorona.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.blank.lawancorona.data.model.TotalKasus
import dev.blank.lawancorona.data.network.RetrofitClient
import dev.blank.lawancorona.data.remote.TotalKasusService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainRepository {
    var totalKasusPositif: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()
    var totalKasusSembuh: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()
    var totalKasusMeninggal: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()


    init {
        getTotalKasus("Positif")
        getTotalKasus("Sembuh")
        getTotalKasus("Meninggal")
    }


    private fun getTotalKasus(kasus: String) {
        val retrofit: Retrofit = RetrofitClient.service
        var totalKasus: Call<TotalKasus>? = null
        when (kasus) {
            "Positif" -> {
                totalKasus = retrofit.create(TotalKasusService::class.java).getTotalKasusPositif()
            }
            "Sembuh" -> {
                totalKasus = retrofit.create(TotalKasusService::class.java).getTotalKasusSembuh()
            }
            "Meninggal" -> {
                totalKasus = retrofit.create(TotalKasusService::class.java).getTotalKasusMeninggal()
            }

        }

        totalKasus!!.enqueue(object : Callback<TotalKasus> {
            override fun onFailure(call: Call<TotalKasus>, t: Throwable) {
                Log.e("LawanCorona", "Tidak terhubung ke Server")
            }

            override fun onResponse(call: Call<TotalKasus>, response: Response<TotalKasus>) {
                if (response.isSuccessful) {
                    when (kasus) {
                        "Positif" -> {
                            totalKasusPositif!!.postValue(response.body())
                        }
                        "Sembuh" -> {
                            totalKasusSembuh!!.postValue(response.body())
                        }
                        "Meninggal" -> {
                            totalKasusMeninggal!!.postValue(response.body())
                        }
                    }
                } else {
                    Log.e("LawanCorona", "Tidak terhubung ke Server")
                }
            }

        })
    }
}
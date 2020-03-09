package dev.blank.lawancorona.viewmodel.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.blank.lawancorona.data.model.TotalKasus
import dev.blank.lawancorona.data.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var repository: MainRepository = MainRepository()
    var totalKasusPositif: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()
    var totalKasusSembuh: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()
    var totalKasusMeninggal: MutableLiveData<TotalKasus?>? = MutableLiveData<TotalKasus?>()


    init {
        totalKasusPositif = repository.totalKasusPositif
        totalKasusSembuh = repository.totalKasusSembuh
        totalKasusMeninggal = repository.totalKasusMeninggal

    }


}
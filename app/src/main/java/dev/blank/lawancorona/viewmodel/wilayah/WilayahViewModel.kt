package dev.blank.lawancorona.viewmodel.wilayah

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.blank.lawancorona.data.model.Kasus
import dev.blank.lawancorona.data.repository.WilayahRepository

class WilayahViewModel(application: Application) : AndroidViewModel(application) {
    var repository: WilayahRepository = WilayahRepository(application)
    var kasus: MutableLiveData<List<Kasus>>? = MutableLiveData<List<Kasus>>()
    var kasusFilter: MutableLiveData<Kasus>? = MutableLiveData<Kasus>()


    init {
        kasus = repository.listKasus
        kasusFilter = repository.kasusFilter
    }

    fun getUpdate() {
        repository.filterWilayah()
    }


}
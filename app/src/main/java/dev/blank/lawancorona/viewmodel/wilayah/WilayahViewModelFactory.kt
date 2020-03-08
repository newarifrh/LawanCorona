package dev.blank.lawancorona.viewmodel.wilayah

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.blank.lawancorona.viewmodel.main.MainViewModel

class WilayahViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WilayahViewModel(application) as T
    }

}
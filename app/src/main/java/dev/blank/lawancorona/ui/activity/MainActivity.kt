package dev.blank.lawancorona.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.blank.lawancorona.R
import dev.blank.lawancorona.data.local.Preferences
import dev.blank.lawancorona.databinding.ActivityMainBinding
import dev.blank.lawancorona.viewmodel.main.MainViewModel
import dev.blank.lawancorona.viewmodel.main.MainViewModelFactory
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModel
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModelFactory


class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    private var mainViewModel: MainViewModel? = null
    private var wilayahViewModel: WilayahViewModel? = null
    private val resultWilayahActivity: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        wilayahViewModel = ViewModelProvider(this, WilayahViewModelFactory(application)).get(WilayahViewModel::class.java)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        activityMainBinding!!.tvWilayah.text = Preferences.getWilayah(this)
        activityMainBinding!!.tvJudul.text = getString(R.string.daftar_kasus_corona_di_custom, Preferences.getWilayah(this))
        activityMainBinding!!.layoutWilayah.setOnClickListener {
            val intent = Intent(this, WilayahActivity::class.java)
            startActivityForResult(intent, resultWilayahActivity)
        }
    }

    private fun setupViewModel() {
        if (Preferences.getWilayah(this) == getString(R.string.dunia)) {


            mainViewModel!!.totalKasusPositif!!
                    .observe(this, Observer { kasus -> activityMainBinding!!.tvTotalKasusPositif.text = kasus!!.value })

            mainViewModel!!.totalKasusSembuh!!
                    .observe(this, Observer { kasus -> activityMainBinding!!.tvTotalKasusSembuh.text = kasus!!.value })

            mainViewModel!!.totalKasusMeninggal!!
                    .observe(this, Observer { kasus -> activityMainBinding!!.tvTotalKasusMeninggal.text = kasus!!.value })
        } else {
            wilayahViewModel!!.kasusFilter!!.observe(this, Observer { kasus ->
                activityMainBinding!!.tvTotalKasusPositif.text = kasus!!.confirmed!!.toString()
                activityMainBinding!!.tvTotalKasusSembuh.text = kasus.recovered!!.toString()
                activityMainBinding!!.tvTotalKasusMeninggal.text = kasus.deaths!!.toString()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == resultWilayahActivity) {
            if (resultCode == Activity.RESULT_OK) {
                setupView()
                setupViewModel()
                wilayahViewModel!!.getUpdate()
            } else {
                println("CANCEL")
            }
        }

    }
}
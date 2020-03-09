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
import dev.blank.lawancorona.util.Converter
import dev.blank.lawancorona.viewmodel.main.MainViewModel
import dev.blank.lawancorona.viewmodel.main.MainViewModelFactory
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModel
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModelFactory
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    private var mainViewModel: MainViewModel? = null
    private var wilayahViewModel: WilayahViewModel? = null
    private val resultWilayahActivity: Int = 1
    private var kasusPositif: String? = "0"
    private var kasusSembuh: String? = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        wilayahViewModel = ViewModelProvider(this, WilayahViewModelFactory(application)).get(WilayahViewModel::class.java)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        if (Preferences.getWilayah(this) == "ALL") {
            activityMainBinding!!.tvWilayah.text = getString(R.string.dunia)
            activityMainBinding!!.tvJudul.text = getString(R.string.daftar_kasus_corona_di_custom, getString(R.string.dunia))
        } else {
            activityMainBinding!!.tvWilayah.text = Preferences.getWilayah(this)
            activityMainBinding!!.tvJudul.text = getString(R.string.daftar_kasus_corona_di_custom, Preferences.getWilayah(this))

        }

        activityMainBinding!!.layoutWilayah.setOnClickListener {
            val intent = Intent(this, WilayahActivity::class.java)
            startActivityForResult(intent, resultWilayahActivity)
        }
    }

    private fun setupViewModel() {
        if (Preferences.getWilayah(this) == "ALL") {


            mainViewModel!!.totalKasusPositif!!
                    .observe(this, Observer { kasus ->
                        activityMainBinding!!.tvTotalKasusPositif.text = kasus!!.value
                        kasusPositif = kasus.value

                        activityMainBinding!!.tvPresentase.text = "${getString(R.string.presentase_custom, Converter.getPresentase(kasusSembuh, kasusPositif))}%"
                    })

            mainViewModel!!.totalKasusSembuh!!
                    .observe(this, Observer { kasus ->
                        activityMainBinding!!.tvTotalKasusSembuh.text = kasus!!.value
                        kasusSembuh = kasus.value

                        activityMainBinding!!.tvPresentase.text = "${getString(R.string.presentase_custom, Converter.getPresentase(kasusSembuh, kasusPositif))}%"

                    })

            mainViewModel!!.totalKasusMeninggal!!
                    .observe(this, Observer { kasus -> activityMainBinding!!.tvTotalKasusMeninggal.text = kasus!!.value })


            activityMainBinding!!.tvPembaharuanTerakhir.text = getString(R.string.pembaharuan_terakhir_custom, Converter.getDateTime(System.currentTimeMillis().toString()))
        } else {
            wilayahViewModel!!.kasusFilter!!.observe(this, Observer { kasus ->
                activityMainBinding!!.tvTotalKasusPositif.text = NumberFormat.getNumberInstance(Locale.US).format(kasus.confirmed!!)
                activityMainBinding!!.tvTotalKasusSembuh.text = NumberFormat.getNumberInstance(Locale.US).format(kasus.recovered!!)
                activityMainBinding!!.tvTotalKasusMeninggal.text = NumberFormat.getNumberInstance(Locale.US).format(kasus.deaths!!)
                activityMainBinding!!.tvPembaharuanTerakhir.text = getString(R.string.pembaharuan_terakhir_custom, kasus.lastUpdate.toString())

                activityMainBinding!!.tvPresentase.text = "${getString(R.string.presentase_custom, Converter.getPresentase(kasus.recovered.toString(), kasus.confirmed.toString()))}%"
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
            }
        }

    }
}
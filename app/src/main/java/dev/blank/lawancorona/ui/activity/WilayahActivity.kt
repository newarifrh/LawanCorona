package dev.blank.lawancorona.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.blank.lawancorona.R
import dev.blank.lawancorona.databinding.ActivityWilayahBinding
import dev.blank.lawancorona.ui.adapter.WilayahDataAdapter
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModel
import dev.blank.lawancorona.viewmodel.wilayah.WilayahViewModelFactory

class WilayahActivity : AppCompatActivity() {
    private var activityWilayahBinding: ActivityWilayahBinding? = null
    private var mainViewModel: WilayahViewModel? = null
    private var wilayahViewModel: WilayahViewModel? = null
    private var adapter: WilayahDataAdapter? = null
    private var loading: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWilayahBinding = DataBindingUtil.setContentView(this, R.layout.activity_wilayah)
        mainViewModel = ViewModelProvider(this, WilayahViewModelFactory(application)).get(WilayahViewModel::class.java)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        val recyclerView: RecyclerView = activityWilayahBinding!!.recyclerView
        loading = activityWilayahBinding!!.loading
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = WilayahDataAdapter(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        wilayahViewModel = ViewModelProvider(
                this,
                WilayahViewModelFactory(application)
        ).get(WilayahViewModel::class.java)
        wilayahViewModel!!.kasus!!
                .observe(this, Observer { kasus ->
                    adapter!!.setKasusList(kasus)
                })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
    }
}

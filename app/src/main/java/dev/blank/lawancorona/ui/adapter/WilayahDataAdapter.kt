package dev.blank.lawancorona.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.blank.lawancorona.R
import dev.blank.lawancorona.data.local.Preferences
import dev.blank.lawancorona.data.model.Kasus
import dev.blank.lawancorona.databinding.WilayahListItemBinding
import dev.blank.lawancorona.ui.activity.WilayahActivity


class WilayahDataAdapter(private var wilayahActivity: WilayahActivity) : RecyclerView.Adapter<WilayahDataAdapter.WilayahViewHolder>() {
    private var kasusList: List<Kasus>? = null
    override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            i: Int
    ): WilayahViewHolder {
        val wilayahListItemBinding: WilayahListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(wilayahActivity),
                R.layout.wilayah_list_item, viewGroup, false
        )
        return WilayahViewHolder(wilayahListItemBinding)
    }

    override fun onBindViewHolder(
            wilayahViewHolder: WilayahViewHolder,
            i: Int
    ) {
        val kasus = kasusList!![i]
        wilayahViewHolder.wilayahListItemBinding.kasus = kasus
        wilayahViewHolder.wilayahListItemBinding.root.findViewById<LinearLayout>(R.id.layoutWilayah)
                .setOnClickListener {
                    if (kasus.id == -1) {
                        Preferences.setWilayah(wilayahActivity, "ALL")
                    } else {
                        Preferences.setWilayah(wilayahActivity, kasus.countryRegion)
                    }

                    val returnIntent = Intent()
                    wilayahActivity.setResult(Activity.RESULT_OK, returnIntent)
                    wilayahActivity.finish()

                }
    }


    override fun getItemCount(): Int {
        return if (kasusList != null) {
            kasusList!!.size
        } else {
            0
        }
    }

    fun setKasusList(kasusList: List<Kasus>?) {
        this.kasusList = kasusList
        notifyDataSetChanged()
    }

    inner class WilayahViewHolder(val wilayahListItemBinding: WilayahListItemBinding) :
            RecyclerView.ViewHolder(wilayahListItemBinding.root)


}
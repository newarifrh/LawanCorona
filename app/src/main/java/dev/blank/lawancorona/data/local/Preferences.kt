package dev.blank.lawancorona.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object Preferences {
    private const val KEY_WILAYAH = "wilayah"
    private fun getSharedPreference(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setWilayah(context: Context, wilayah: String?) {
        val editor = getSharedPreference(context).edit()
        editor.putString(KEY_WILAYAH, wilayah)
        editor.apply()
    }

    fun getWilayah(context: Context): String? {
        return getSharedPreference(context).getString(KEY_WILAYAH, "ALL")
    }
}
package dev.blank.lawancorona.util

import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun getDateTime(timestamp: String): String? {
        return try {
            val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
            val netDate = Date(timestamp.toLong())
            sdf.format(netDate)
        } catch (e: Exception) {
            ""
        }
    }

    fun getPresentase(sembuh: String?, total: String?): Float {
        return if (sembuh == null && total == null) {
            0F
        } else {
            val sembuhFloat = sembuh!!.replace(",", "").toFloat()
            val totalFloat = total!!.replace(",", "").toFloat()
            (sembuhFloat / totalFloat) * 100
        }
    }
}
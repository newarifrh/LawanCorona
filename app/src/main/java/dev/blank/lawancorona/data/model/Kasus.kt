package dev.blank.lawancorona.data.model

import com.google.gson.annotations.SerializedName

class Kasus {
    @SerializedName("OBJECTID")
    var id: Int? = null

    @SerializedName("Country_Region")
    var countryRegion: String? = null

    @SerializedName("Last_Update")
    var lastUpdate: String? = null

    @SerializedName("Confirmed")
    var confirmed: Int? = null

    @SerializedName("Deaths")
    var deaths: Int? = null

    @SerializedName("Recovered")
    var recovered: Int? = null

}
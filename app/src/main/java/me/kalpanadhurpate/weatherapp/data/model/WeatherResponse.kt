package me.kalpanadhurpate.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(  // @SerializedName("coord"      ) var coord      : Coord,
    @SerializedName("weather"    ) var weather    : ArrayList<Weather>,
    @SerializedName("base"       ) var base       : String,
    @SerializedName("main"       ) var main       : Main,
    @SerializedName("visibility" ) var visibility : Int,
    @SerializedName("sys"        ) var sys        : Sys,
    @SerializedName("timezone"   ) var timezone   : Int,
    @SerializedName("id"         ) var id         : Int,
    @SerializedName("name"       ) var name       : String,
    @SerializedName("cod"        ) var cod        : Int

)
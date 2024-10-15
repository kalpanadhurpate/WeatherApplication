package me.kalpanadhurpate.weatherapp.data.model

import com.google.gson.annotations.SerializedName


data class LocalNames (

  @SerializedName("fr" ) var fr : String? = null,
  @SerializedName("ru" ) var ru : String? = null

)
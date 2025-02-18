package com.example.mypocapp.data.models.userdata

import com.google.gson.annotations.SerializedName


data class UserResponse (

  @SerializedName("page"        ) var page       : Int?            = null,
  @SerializedName("per_page"    ) var perPage    : Int?            = null,
  @SerializedName("total"       ) var total      : Int?            = null,
  @SerializedName("total_pages" ) var totalPages : Int?            = null,
  @SerializedName("data"        ) var data       : ArrayList<Data> = arrayListOf(),
  @SerializedName("support"     ) var support    : Support?        = Support()

)
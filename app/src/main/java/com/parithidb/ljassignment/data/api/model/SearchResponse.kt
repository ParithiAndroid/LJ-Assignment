package com.parithidb.ljassignment.data.api.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<GHRepo>
)
package com.parithidb.ljassignment.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * API response for GitHub repository search.
 *
 * @property items List of repositories returned by the API
 */

data class SearchResponse(
    @SerializedName("items") val items: List<GHRepo>
)
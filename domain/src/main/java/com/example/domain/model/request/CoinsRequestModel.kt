package com.example.domain.model.request

import com.example.core.Constants

data class CoinsRequestModel(
    var searchQuery: String = "",
) {
    var limit: String = "${Constants.PAGE_LIMIT}"    //default limit
    var offset: String = "0"    //default offset
    var orderBy: String = ""
}

fun CoinsRequestModel.toQuery(): Map<String, String> {
    return hashMapOf<String, String>().apply {

        if (searchQuery.isNotEmpty()) {
            put("search", searchQuery)
        }

        if (orderBy.isNotEmpty()) {
            put("orderBy", orderBy)
        }

        put("limit", limit)
        put("offset", offset)

    }
}

fun CoinsRequestModel.toQuerySymbol(): Map<String, String> {
    return hashMapOf<String, String>().apply {

        if (searchQuery.isNotEmpty()) {
            put("symbol", searchQuery)
        }

    }
}
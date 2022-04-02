package com.example.data.ext

import android.content.Context
import com.example.core.R
import com.example.core.ext.to2DecimalFormat

fun Double?.getCoinColor(): Int {
    return this?.let { _change ->
        when {
            _change >= 0 -> R.color.green
            else -> R.color.red
        }
    } ?: run {
        R.color.green
    }
}

fun Double?.getChangeIcon(): Int {
    return this?.let { _change ->
        when {
            _change >= 0 -> R.drawable.ic_arrow_up
            else -> R.drawable.ic_arrow_down
        }
    } ?: run {
        R.drawable.ic_arrow_up
    }
}

fun Long?.toPriceWording(context: Context, prefix: String): String {

    val text = StringBuilder()

    if (this == null) {
        text.append(0)
    } else {
        var amount = 0L
        if (this >= 10000000000000) {
            amount = this / 10000000000000
            text.append("$prefix ${amount.to2DecimalFormat()} ${context.getString(R.string.common_trilion)}")
        } else if (this >= 1000000000) {
            amount = this / 1000000000
            text.append("$prefix ${amount.to2DecimalFormat()} ${context.getString(R.string.common_billion)}")
        } else if (this >= 1000000) {
            amount = this / 1000000
            text.append("$prefix ${amount.to2DecimalFormat()} ${context.getString(R.string.common_million)}")
        } else {
            text.append("$prefix ${amount.to2DecimalFormat()}")
        }
    }

    return text.toString()
}
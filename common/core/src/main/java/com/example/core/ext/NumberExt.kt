package com.example.core.ext

import java.text.DecimalFormat


val format2Decimal = DecimalFormat("###,###,###,###,###,##0.00")
val format5Decimal = DecimalFormat("###,###,###,###,###,##0.00000")

fun Double?.to2DecimalFormat(prefix: String = ""): String {
    val amount = if (this == null) {
        0.0
    } else {
        this
    }

    if (prefix.isNotEmpty()) {
        return "${prefix}${format2Decimal.format(amount)}"

    } else {
        return format2Decimal.format(amount)
    }
}

fun Long?.to2DecimalFormat(prefix: String = ""): String {
    val amount = if (this == null) {
        0.0
    } else {
        this
    }

    if (prefix.isNotEmpty()) {
        return "${prefix}${format2Decimal.format(amount)}"

    } else {
        return format2Decimal.format(amount)
    }
}

fun Double?.to5DecimalFormat(prefix: String = ""): String {
    val amount = if (this == null) {
        0.0
    } else {
        this
    }

    if (prefix.isNotEmpty()) {
        return "${prefix}${format5Decimal.format(amount)}"

    } else {
        return format5Decimal.format(amount)
    }
}


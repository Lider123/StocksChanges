package ru.babaets.stockchanges

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.NumberFormat
import java.util.*

fun Float.toCurrency(): String = NumberFormat.getCurrencyInstance().apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
    currency = Currency.getInstance("EUR")
}.format(this)

fun Float.toFormattedString(trailingNumbersCount: Int): String {
    if (trailingNumbersCount < 0) throw IllegalArgumentException("Trailing numbers count can't be less then 0")

    return String.format("%.${trailingNumbersCount}f", this)
}

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

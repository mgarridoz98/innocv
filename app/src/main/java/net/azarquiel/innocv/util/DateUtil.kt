package net.azarquiel.innocv.util

import java.text.SimpleDateFormat
import java.util.Locale
// Extension Function
fun String.formatDate(): String {
    val locale = Locale("es", "ES")
    var formatter = SimpleDateFormat("yyyy-MM-dd", locale)
    val date = formatter.parse(this)
    date?.let {
        return formatter.format(it)
    }
    return ""
}
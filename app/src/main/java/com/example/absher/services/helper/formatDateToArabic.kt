package com.example.absher.services.helper

import android.icu.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateToArabic(inputDate: String): String {
    try {// Parse the input date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val date: Date = inputFormat.parse(inputDate) ?: return "not available"

        // Format the date in Arabic
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))
        val formattedDate = outputFormat.format(date)

        // Convert numbers to Arabic numerals
        return convertToArabicNumbers(formattedDate)
    } catch (_: Exception) {
        return  "not available"
    }
}

// Function to convert numbers to Arabic numerals
fun convertToArabicNumbers(input: String): String {
    val numberFormat = NumberFormat.getInstance(Locale("ar"))
    return input.map { char ->
        if (char.isDigit()) numberFormat.format(char.toString().toInt()) else char
    }.joinToString("")
}


fun formatTimeToArabic(inputTime: String): String {
    try {// Parse the 24-hour time
        val inputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val date: Date = inputFormat.parse(inputTime) ?: return ""

        // Convert to 12-hour format with Arabic AM/PM
        val outputFormat = SimpleDateFormat("hh:mm a", Locale("ar"))
        var formattedTime = outputFormat.format(date)

        // Replace AM/PM with Arabic equivalents
        formattedTime = formattedTime.replace("AM", "ص").replace("PM", "م")

        // Convert digits to Arabic numerals
        return convertToArabicNumbers(formattedTime)
    } catch (e: Exception) {
        return "not available"
    }
}



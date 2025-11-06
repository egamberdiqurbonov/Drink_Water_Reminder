package uz.egam.drinkwaterreminder.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NOTIFICATION_CHANNEL_ID: String = "my_notification_channel_id"
const val NOTIFICATION_CHANNEL_NAME: String = "my_notification_channel_name"

fun Long.getHour(): String {

    val timeMillis = this
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = dateFormat.format(Date(timeMillis))

    return formattedTime
}

const val UnitMetric = "Metric"
const val UnitImperial = "Imperial"

const val NOT_VERY_ACTIVE = "Not very active"
const val LIGHTLY_ACTIVE = "Lightly active"
const val ACTIVE = "Active"
const val HIGH_ACTIVE = "Highly active"

data class DayAmount(
    val dayOfWeek: String,
    val totalAmount: Int
)
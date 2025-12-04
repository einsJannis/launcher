package dev.einsjannis.uindex.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.res.stringResource
import dev.einsjannis.uindex.R
@SuppressLint("LocalContextConfigurationRead")
@Composable
fun Calendar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dateFormatPattern = stringResource(R.string.date_format_day_month)
    val currentLocale = context.resources.configuration.locales.get(0)
    Row(modifier.clip(RoundedCornerShape(10.dp)).clickable {
        context.startActivity(Intent(Intent.ACTION_MAIN)
            .apply {
                addCategory(Intent.CATEGORY_APP_CALENDAR)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
    }) {
        var date by remember { mutableStateOf(LocalDate.now()) }
        LaunchedEffect(Unit) {
            while (true) {
                date = LocalDate.now()
                delay(1000)
            }
        }
        Text(
            DateTimeFormatter.ofPattern(dateFormatPattern, currentLocale).format(date),
            modifier = Modifier.padding(horizontal = 10.dp)
        )    }
}
package dev.einsjannis.launcher.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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

@Composable
fun Calendar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier.clip(RoundedCornerShape(10.dp)).clickable {
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
        Text(DateTimeFormatter.ofPattern("EE, d. LLL").format(date), modifier = Modifier.padding(horizontal = 10.dp))
    }
}
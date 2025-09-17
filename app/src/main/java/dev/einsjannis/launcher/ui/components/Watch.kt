package dev.einsjannis.launcher.ui.components

import android.content.Intent
import android.provider.AlarmClock
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun Watch(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier.clip(RoundedCornerShape(10.dp)).clickable(onClick = {
        context.startActivity(Intent(AlarmClock.ACTION_SHOW_ALARMS)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
    })) {
        var time by remember { mutableStateOf(LocalTime.now()) }
        LaunchedEffect(Unit) {
            while (true) {
                time = LocalTime.now()
                delay(1000)
            }
        }
        Text(
            time.format(DateTimeFormatter.ofPattern("HH:mm")),
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 60.sp
        )
    }
}
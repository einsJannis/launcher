package dev.einsjannis.launcher.list

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.menu.Menu
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeItem: Item() {
    @Composable
    override fun Element(modifier: Modifier, menu: MutableState<Menu?>) {
        val context = LocalContext.current
        Box(modifier = modifier.clickable {
            context.startActivity(
                Intent(AlarmClock.ACTION_SHOW_ALARMS)
                    .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
            )
        }) {
            var time by remember { mutableStateOf(LocalTime.now()) }

            LaunchedEffect(Unit) {
                while (true) {
                    time = LocalTime.now()
                    delay(1000)
                }
            }

            Title(
                time.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = TextStyle(fontSize = 80.sp),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}
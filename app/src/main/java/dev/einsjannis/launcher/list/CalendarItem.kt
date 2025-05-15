package dev.einsjannis.launcher.list

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.menu.Menu
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarItem: Item() {
    @Composable
    override fun Element(modifier: Modifier, menu: MutableState<Menu?>) {
        val context = LocalContext.current
        Box(modifier = modifier.clickable {
            context.startActivity(
                Intent(Intent.ACTION_MAIN)
                    .apply {
                        addCategory(Intent.CATEGORY_APP_CALENDAR)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
            )
        }) {
            var time by remember { mutableStateOf(LocalDate.now()) }

            LaunchedEffect(Unit) {
                while (true) {
                    time = LocalDate.now()
                    delay(1000)
                }
            }

            Title(
                time.format(DateTimeFormatter.ofPattern("d. LLL yyyy")),
                modifier = modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
            )
        }
    }
}
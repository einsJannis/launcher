package dev.einsjannis.launcher

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import dev.einsjannis.launcher.list.AppItem
import dev.einsjannis.launcher.list.CalendarItem
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.list.Items
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.list.TimeItem
import dev.einsjannis.launcher.ui.theme.LauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appItems = AppInfo.allApps(applicationContext).map { AppItem(it) }.toMutableList()
        val items = appItems.zipWithNext().flatMap { (current, next) ->
            if (current.app.name.first() == next.app.name.first()) {
                listOf(current)
            } else {
                listOf(current, HeaderItem(next.app.name.first().toString()))
            }
        }.toMutableList()
        items.addLast(appItems.last())
        items.addFirst(HeaderItem(appItems.first().app.name.first().toString()))
        items.addFirst(CalendarItem())
        items.addFirst(TimeItem())
        items.addFirst(SpaceItem())
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Items(items).Element(this@MainActivity)
                }
            }
        }
    }
}

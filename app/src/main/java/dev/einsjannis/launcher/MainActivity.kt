package dev.einsjannis.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.einsjannis.launcher.list.CalendarItem
import dev.einsjannis.launcher.list.HeaderItem
import dev.einsjannis.launcher.list.Item
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.list.TimeItem
import dev.einsjannis.launcher.ui.theme.LauncherTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val items = listOf(SpaceItem(), TimeItem(), CalendarItem())
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val listState = rememberLazyListState()
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn (state = listState, modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                            items(items) { item ->
                                item.Element(applicationContext)
                            }
                        }
                    }
                    (application as Launcher).apps.ElementScrollBar(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { startActivity(Intent(this@MainActivity, ListActivity::class.java)) }
                    )
                }
            }
        }
    }
}

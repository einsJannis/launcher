package dev.einsjannis.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.list.CalendarItem
import dev.einsjannis.launcher.list.SpaceItem
import dev.einsjannis.launcher.list.TimeItem
import dev.einsjannis.launcher.menu.Menu
import dev.einsjannis.launcher.ui.theme.LauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val items = listOf(SpaceItem(), TimeItem(), CalendarItem())
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val menu: MutableState<Menu?> = remember { mutableStateOf(null) }
                    val listState = rememberLazyListState()
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn (state = listState, modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                            items(items) { item ->
                                item.Element(applicationContext, menu)
                            }
                        }
                    }
                    (application as Launcher).apps.ElementScrollBar(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { startActivity(Intent(this@MainActivity, ListActivity::class.java)) }
                    )
                    menu.value?.also {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x88000000))
                                .clickable {
                                    menu.value = null
                                }
                        ) {
                            BackHandler {
                                menu.value = null
                            }
                            it.Element(
                                applicationContext,
                                Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

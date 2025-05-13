package dev.einsjannis.launcher.menu

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.einsjannis.launcher.list.AppItem

class Menu(val appItem: AppItem, private val buttons: List<Button>) {
    @Composable
    fun Element(context: Context, modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            tonalElevation = 8.dp
        ) {
            Column(modifier = modifier.fillMaxWidth().background(Color.DarkGray)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(80.dp)) {
                    appItem.Icon(context, Modifier.padding(20.dp))
                    appItem.Title(context, appItem.app.name)
                }
                HorizontalDivider()
                Row(modifier = Modifier.height(200.dp)) {
                    Column {
                        for (button in buttons) {
                            button.Element(
                                context,
                                this@Menu,
                                modifier = Modifier.height(80.dp).fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
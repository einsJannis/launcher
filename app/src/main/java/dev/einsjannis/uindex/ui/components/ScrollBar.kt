package dev.einsjannis.uindex.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScrollBar(scrollBar: MutableScrollBarState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.pointerInput(Unit, scrollBar.handlePointerInput).width(60.dp).padding(start = 27.dp, end = 7.dp)
    ) {
        val categoryIndices by scrollBar.list.categoryIndices.collectAsState()
        val items by remember { derivedStateOf { categoryIndices.map { scrollBar.list.allCategories[it] } } }
        items.iterator().withIndex().forEach { (index, category) ->
            val offsetIfHeld = -scrollBar.offset(index).dp
            val offset = animateDpAsState(if (scrollBar.isHeld) offsetIfHeld else 0.dp)
            val modifier = Modifier.offset(x = offset.value)
            Text(category.title, modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(5.dp)), textAlign = TextAlign.Center, fontSize = 15.sp, lineHeight = 20.sp)
        }
    }
}
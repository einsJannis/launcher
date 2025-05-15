package dev.einsjannis.launcher

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun currentActivity(): Activity? {
    val context = LocalContext.current
    return remember(context) {
        var ctx = context
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return@remember ctx
            ctx = ctx.baseContext
        }
        null
    }
}

fun <T: U, U> List<T>.insertBetween(f: (prev: T?, next: T?) -> U?): List<U> {
    return buildList(this.size) {
        for (element in this@insertBetween) {
            // SAFETY: at the end of each iteration we set the last element to one of type T and
            //         in the beginning the list is empty
            f(lastOrNull() as T?, element)?.also { addLast(it) }
            addLast(element)
        }
        // SAFETY: at the end of each iteration we set the last element to one of type T and
        //         in the beginning the list is empty
        f(lastOrNull() as T?, null)?.also { addLast(it) }
    }
}
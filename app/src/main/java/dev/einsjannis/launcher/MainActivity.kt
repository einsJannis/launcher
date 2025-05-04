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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import dev.einsjannis.launcher.ui.theme.LauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauncherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    AppList(this@MainActivity, AppInfo.allApps(this@MainActivity.applicationContext))
                }
            }
        }
    }
}

class AppInfo {
    val icon: Drawable
        get() = resolveInfo.loadIcon(pm)
    val name: String
        get() = resolveInfo.loadLabel(pm).toString()
    val launchIntent
        get() = pm.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)

    private val pm: PackageManager
    private val resolveInfo: ResolveInfo

    constructor(context: Context, packageName: String, activityName: String) {
        pm = context.packageManager
        var intent = Intent()
        intent.component = ComponentName(packageName, activityName)
        resolveInfo = pm.resolveActivity(intent, 0)!!
    }
    constructor(context: Context, resolveInfo: ResolveInfo) {
        this.pm = context.packageManager
        this.resolveInfo = resolveInfo
    }

    companion object {
        fun allApps(context: Context): List<AppInfo> {
            var intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
                }
            val res = context.packageManager.queryIntentActivities(intent, 0)
                .map { AppInfo(context, it) }
                .sortedBy { it.name }
            return res
        }
    }
}

@Composable
fun AppList(activity: Activity, apps: List<AppInfo>) {
    LazyColumn {
        items(apps) { app ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                activity.startActivity(app.launchIntent)
            }.fillMaxWidth().height(90.dp).padding(5.dp)) {
                Image(
                    app.icon.toBitmap().asImageBitmap(), app.name + " Icon",
                    modifier = Modifier.fillMaxHeight().width(80.dp).padding(10.dp)
                )
                Text(app.name, modifier = Modifier.padding(start = 10.dp), style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    //fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 7f
                    )
                ))
            }
        }
    }
}

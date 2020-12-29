package com.qwert2603.eten

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import com.qwert2603.eten.android.EtenCallbacksAndroid
import com.qwert2603.eten.presentation.EtenApp

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration().apply {
            E.env.forceLocale?.also { setLocale(it) }
        }
        val configuredContext = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(configuredContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val etenCallbacks = EtenCallbacksAndroid(this)

        setContent {
            EtenApp(etenCallbacks)
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    EtenApp()

    val list = listOf("een", "twee", "drie", "vier", "vijf", "zes")
}
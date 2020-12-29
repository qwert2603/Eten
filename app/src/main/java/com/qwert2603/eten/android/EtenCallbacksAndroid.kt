package com.qwert2603.eten.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.qwert2603.eten.BuildConfig
import com.qwert2603.eten.EtenCallbacks
import java.io.File

class EtenCallbacksAndroid(
    private val activity: AppCompatActivity,
) : EtenCallbacks {

    override fun sendDump(file: File) {
        val uri = FileProvider.getUriForFile(
            activity,
            BuildConfig.APPLICATION_ID,
            file,
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.setDataAndType(uri, "application/json")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        activity.startActivity(Intent.createChooser(intent, ""))
    }
}
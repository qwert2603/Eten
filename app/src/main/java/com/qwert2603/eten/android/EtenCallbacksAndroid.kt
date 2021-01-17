package com.qwert2603.eten.android

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.qwert2603.eten.BuildConfig
import com.qwert2603.eten.EtenCallbacks
import com.qwert2603.eten.R
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import java.io.File

class EtenCallbacksAndroid(
    private val activity: AppCompatActivity,
) : EtenCallbacks {

    private val openDumpResult = Channel<String?>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val openDumpLauncher = activity.registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri == null) {
            openDumpResult.offer(null)
            return@registerForActivityResult
        }
        val dumpJson = activity.contentResolver.openInputStream(uri)!!
            .use { String(it.readBytes()) }
        openDumpResult.offer(dumpJson)
    }

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

    override suspend fun openDump(): String? {
        openDumpLauncher.launch("*/*")
        return openDumpResult.receive()
    }

    override fun searchCalorie(product: String) {
        val searchQuery = activity.getString(R.string.search_query_calorie, product)
        val uri = "https://www.google.com/search?q=$searchQuery"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity.startActivity(intent)
    }
}
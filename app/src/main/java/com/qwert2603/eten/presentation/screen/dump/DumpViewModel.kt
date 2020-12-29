package com.qwert2603.eten.presentation.screen.dump

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwert2603.eten.data.repo_impl.DumpRepoImpl
import com.qwert2603.eten.domain.repo.DumpRepo
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import java.io.File

class DumpViewModel(
    private val dumpRepo: DumpRepo = DumpRepoImpl,
) : ViewModel() {

    private val sendDumpChannel = BroadcastChannel<File>(Channel.CONFLATED)
    private val dumpRestoredChannel = BroadcastChannel<Any>(Channel.CONFLATED)

    val sendDumpEvents: Flow<File> = sendDumpChannel.asFlow()
    val dumpRestoredEvents: Flow<Any> = dumpRestoredChannel.asFlow()

    fun onSaveDumpClick() {
        viewModelScope.launch {
            sendDumpChannel.offer(dumpRepo.getDump())
        }
    }

    fun onRestoreDumpClick(dump: String) {
        viewModelScope.launch {
            val restoreSuccess = dumpRepo.restoreDump(dump)
            if (restoreSuccess) {
                dumpRestoredChannel.offer(Any())
            }
        }
    }
}
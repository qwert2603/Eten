package com.qwert2603.eten.presentation.screen.dump

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.qwert2603.eten.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenDump(
    navigateUp: () -> Unit,
    sendDump: (File) -> Unit,
    openDump: suspend () -> String?,
) {
    val vm = viewModel<DumpViewModel>()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val dumpRestoredMessage = stringResource(R.string.dump_restored_message)
    LaunchedEffect("DumpScreen") {
        vm.sendDumpEvents
            .onEach { sendDump(it) }
            .launchIn(this)
        vm.dumpRestoredEvents
            .onEach {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                scaffoldState.snackbarHostState.showSnackbar(dumpRestoredMessage)
            }
            .launchIn(this)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dump_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack)
                    }
                },
            )
        },
    ) {
        ScrollableColumn(contentPadding = PaddingValues(16.dp)) {
            Button(onClick = { vm.onSaveDumpClick() }) {
                Text(stringResource(R.string.dump_save_button))
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        val dump = openDump()
                        Timber.d("openDump ${dump?.length}")
                        dump ?: return@launch
                        vm.onRestoreDumpClick(dump)
                    }
                },
            ) {
                Text(stringResource(R.string.dump_restore_button))
            }
        }
    }
}
package com.qwert2603.eten.presentation.screen.debug

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.util.Catch
import com.qwert2603.eten.util.noContentDescription

@Composable
fun ScreenDebug(
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("debug") },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Default.ArrowBack, noContentDescription)
                    }
                },
            )
        }
    ) {
        ScrollableColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            Button(onClick = { throw Exception("test crash") }) {
                Text("crash")
            }
            Spacer(modifier = Modifier.heightIn(16.dp))
            Button(onClick = { Catch.log("test non-fatal") }) {
                Text("non-fatal")
            }
        }
    }
}
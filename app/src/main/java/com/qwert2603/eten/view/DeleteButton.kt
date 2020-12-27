package com.qwert2603.eten.view

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DeleteButton(
    isEnabled: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Delete,
) {
    IconButton(
        onClick = onDeleteClick,
        enabled = isEnabled,
        modifier = modifier,
    ) {
        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colors.onSurface
                .copy(alpha = if (isEnabled) 1f else 0.5f),
        )
    }
}
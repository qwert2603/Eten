package com.qwert2603.eten.view

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> SelectDropDown(
    items: List<T>,
    selectedItem: T?,
    renderItem: @Composable (T) -> Unit,
    onItemSelected: (T) -> Unit,
    toggleModifier: Modifier = Modifier,
) {
    var expanded by savedInstanceState { false }

    DropdownMenu(
        toggle = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                if (selectedItem != null) {
                    renderItem(selectedItem)
                } else {
                    Text("")
                }
                Icon(Icons.Default.KeyboardArrowDown)
            }
        },
        expanded = expanded,
        onDismissRequest = { expanded = false },
        toggleModifier = toggleModifier,
    ) {
        items.forEach {
            DropdownMenuItem(onClick = {
                expanded = false
                onItemSelected(it)
            }) {
                renderItem(it)
            }
        }
    }
}
package com.qwert2603.eten.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.SaverScope
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable

@Composable
fun <T : Any> AutocompleteTextField(
    selectedItem: T?,
    searchItems: suspend (String) -> List<T>,
    renderItem: @Composable (T) -> Unit,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit,
    toggleModifier: Modifier = Modifier,
) {
    var textFieldValue by savedInstanceState(
        selectedItem,
        saver = TextFieldValueSaver()
    ) {
        val text = selectedItem?.let(itemToString) ?: ""
        TextFieldValue(text = text, selection = TextRange(text.length))
    }
    var expanded by savedInstanceState { false }
    val scope = rememberCoroutineScope()
    var currentJob by remember { mutableStateOf<Job?>(null) }
    var items by remember { mutableStateOf<List<T>>(emptyList()) }

    DropdownMenu(
        toggle = {
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                trailingIcon = {
                    IconButton(onClick = {
                        currentJob?.cancel()
                        currentJob = scope.launch {
                            items = searchItems(textFieldValue.text)
                            expanded = true
                        }
                    }) {
                        Icon(Icons.Default.Search)
                    }
                }
            )
        },
        expanded = expanded,
        onDismissRequest = { expanded = false },
        toggleModifier = toggleModifier,
    ) {
        items.forEach {
            val text = itemToString(it)
            DropdownMenuItem(onClick = {
                expanded = false
                onItemSelected(it)
            }) {
                renderItem(it)
            }
        }
    }
}

class TextFieldValueSaver : Saver<TextFieldValue, TextFieldValueSaver.SerializableTextFieldValue> {

    class SerializableTextFieldValue(
        val text: String,
        val selection: Long,
        val composition: Long?,
    ) : Serializable

    override fun SaverScope.save(value: TextFieldValue) = SerializableTextFieldValue(
        text = value.text,
        selection = value.selection.packedValue,
        composition = value.composition?.packedValue,
    )

    override fun restore(value: SerializableTextFieldValue) = TextFieldValue(
        text = value.text,
        selection = TextRange(value.selection),
        composition = value.composition?.let { TextRange(it) },
    )
}
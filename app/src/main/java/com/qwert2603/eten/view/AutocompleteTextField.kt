package com.qwert2603.eten.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.SaverScope
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.qwert2603.eten.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.Serializable

@Composable
fun <T : Any> AutocompleteTextField(
    fieldId: Any,
    selectedItem: T?,
    searchItems: suspend (String) -> List<T>,
    renderItem: @Composable (T) -> Unit,
    itemToString: (T) -> String,
    onItemSelected: (T?) -> Unit,
    toggleModifier: Modifier = Modifier,
) {
    var textFieldValue by savedInstanceState(
        fieldId,
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
                onValueChange = {
                    Timber.d("onValueChange ${it.text}")
                    if (it.text != textFieldValue.text) {
                        onItemSelected(null)
                    }
                    textFieldValue = it
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface
                        .copy(alpha = if (selectedItem != null) 1f else 0.6f),
                ),
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
                },
            )
        },
        expanded = expanded,
        onDismissRequest = { expanded = false },
        toggleModifier = toggleModifier,
    ) {
        if (items.isEmpty()) {
            DropdownMenuItem(onClick = {
                expanded = false
            }) {
                Text(stringResource(R.string.autocomplete_text_field_nothing_found))
            }
        }
        items.forEach {
            DropdownMenuItem(onClick = {
                expanded = false
                val text = itemToString(it)
                textFieldValue = textFieldValue.copy(
                    text = text,
                    selection = TextRange(text.length),
                )
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
    ) : Serializable // todo: remove import java.io.Serializable

    override fun SaverScope.save(value: TextFieldValue) = SerializableTextFieldValue(
        text = value.text,
        selection = value.selection.packedValue,
    )

    override fun restore(value: SerializableTextFieldValue) = TextFieldValue(
        text = value.text,
        selection = TextRange(value.selection),
    )
}
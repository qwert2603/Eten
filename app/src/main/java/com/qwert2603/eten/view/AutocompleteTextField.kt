package com.qwert2603.eten.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.qwert2603.eten.R
import com.qwert2603.eten.util.noContentDescription
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
    modifier: Modifier = Modifier,
    onQueryChanged: ((String) -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    // todo: rememberSaveable
    // todo: fix usage fieldId for entered text without selected item, when product is deleted and items move up in the list.

    var textFieldValue: TextFieldValue by remember(fieldId) {
        val text = selectedItem?.let(itemToString) ?: ""
        val textFieldValue = TextFieldValue(text = text, selection = TextRange(text.length))
        mutableStateOf(textFieldValue)
    }
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var currentJob by remember { mutableStateOf<Job?>(null) }
    var items by remember { mutableStateOf<List<T>>(emptyList()) }

    Box {
        TextField(
            value = textFieldValue,
            onValueChange = {
                Timber.d("onValueChange ${it.text}")
                if (it.text != textFieldValue.text) {
                    onItemSelected(null)
                }
                onQueryChanged?.invoke(it.text)
                textFieldValue = it
            },
            textStyle = TextStyle(
                color = if (selectedItem != null) Color.Black else Color.Gray,
            ),
            trailingIcon = {
                IconButton(onClick = {
                    currentJob?.cancel()
                    currentJob = scope.launch {
                        items = searchItems(textFieldValue.text)
                        expanded = true
                    }
                }) {
                    Icon(Icons.Default.Search, noContentDescription)
                }
            },
            label = label,
            modifier = modifier,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
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
                    onQueryChanged?.invoke(text)
                }) {
                    renderItem(it)
                }
            }
        }
    }
}

class TextFieldValueSaver : Saver<TextFieldValue, TextFieldValueSaver.SerializableTextFieldValue> {

    // todo: save selection
    class SerializableTextFieldValue(
        val text: String,
    ) : Serializable // todo: remove import java.io.Serializable

    override fun SaverScope.save(value: TextFieldValue) = SerializableTextFieldValue(
        text = value.text,
    )

    override fun restore(value: SerializableTextFieldValue) = TextFieldValue(
        text = value.text,
        selection = TextRange(value.text.length),
    )
}
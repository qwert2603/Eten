package com.qwert2603.eten

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.stub.Stub

@Composable
fun AddProduct(navController: NavController) {
    val name = mutableStateOf("")
    val caloriesPer100g = mutableStateOf(0)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Product") }, actions = {
                IconButton(
                    onClick = {
                        Stub.addProduct(
                            Product(
                                name = name.value,
                                calorie = caloriesPer100g.value / 100.0
                            )
                        )
                        navController.navigateUp()
                    },
                    enabled = name.value.isNotBlank() && caloriesPer100g.value != 0
                ) {
                    Icon(Icons.Default.Done)
                }
            })
        }
    ) {
        Column {
            TextField(
                value = name.value,
                onValueChange = { name.value = it.take(100).trim() },
                placeholder = { Text("Name") },
                modifier = Modifier.padding(12.dp),
            )
            TextField(
                value = caloriesPer100g.value.takeIf { it != 0 }?.toString() ?: "",
                onValueChange = { caloriesPer100g.value = it.take(4).toIntOrNull() ?: 0 },
                placeholder = { Text("Calories per 100g") },
                keyboardType = KeyboardType.Number,
                modifier = Modifier.padding(12.dp),
            )
        }
    }
}

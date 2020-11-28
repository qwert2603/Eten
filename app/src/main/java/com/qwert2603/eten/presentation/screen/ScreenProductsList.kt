package com.qwert2603.eten.presentation.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.data.repo_impl.EtenRepoStub
import com.qwert2603.eten.presentation.list_item.ItemProduct

@Composable
fun ScreenProductsList() {
    val products = EtenRepoStub.productsUpdates().collectAsState(initial = emptyList())
    LazyColumnFor(items = products.value) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            ItemProduct(product = it)
        }
        Divider()
    }
}
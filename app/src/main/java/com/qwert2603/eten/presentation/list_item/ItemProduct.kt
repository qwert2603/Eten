package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.util.formatCalorie

@Composable
fun ItemProduct(product: Product) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
    ) {
        Text(
            product.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )
        Text(product.caloriePer100g.formatCalorie())
    }
}
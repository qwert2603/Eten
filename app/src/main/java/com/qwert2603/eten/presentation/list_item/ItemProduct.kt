package com.qwert2603.eten.presentation.list_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwert2603.eten.domain.model.Product
import com.qwert2603.eten.util.formatCalorie
import com.qwert2603.eten.view.DeleteButton

@Composable
fun ItemProduct(
    product: Product,
    isDeletable: Boolean,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            product.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )
        Text(
            product.caloriePer100g.formatCalorie(),
            modifier = Modifier.padding(start = 12.dp),
        )
        DeleteButton(
            isEnabled = isDeletable,
            onDeleteClick = onDeleteClick,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}
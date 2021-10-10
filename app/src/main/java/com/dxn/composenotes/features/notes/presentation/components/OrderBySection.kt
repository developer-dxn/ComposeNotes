package com.dxn.composenotes.features.notes.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dxn.composenotes.features.notes.presentation.screens.notes.OrderBy
import com.dxn.composenotes.features.notes.presentation.screens.notes.OrderType

@Composable
fun OrderBySection(
    modifier: Modifier = Modifier,
    orderBy: OrderBy = OrderBy.Date(OrderType.Descending),
    onOrderChange : (OrderBy) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            AppRadioButton(
                modifier = modifier,
                text = "Title",
                selected = orderBy is OrderBy.Title
            ) {
                onOrderChange(OrderBy.Title(OrderType.Descending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            AppRadioButton(
                modifier = modifier,
                text = "Date",
                selected = orderBy is OrderBy.Date
            ) {
                onOrderChange(OrderBy.Date(OrderType.Descending))
            }
            AppRadioButton(
                modifier = modifier,
                text = "Color",
                selected = orderBy is OrderBy.Color
            ) {
                onOrderChange(OrderBy.Color(OrderType.Descending))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppRadioButton(
                modifier = modifier,
                text = "Ascending",
                selected = orderBy.orderType is OrderType.Ascending
            ) {
                onOrderChange(orderBy.copy(OrderType.Ascending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            AppRadioButton(
                modifier = modifier,
                text = "Descending",
                selected = orderBy.orderType is OrderType.Descending
            ) {
                onOrderChange(orderBy.copy(OrderType.Descending))
            }
        }
    }

}

@Preview
@Composable
fun Preview() {
    OrderBySection(onOrderChange = { })
}

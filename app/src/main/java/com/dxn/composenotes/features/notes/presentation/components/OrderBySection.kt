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
                onOrderChange(OrderBy.Title(OrderType.Ascending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            AppRadioButton(
                modifier = modifier,
                text = "Date",
                selected = orderBy is OrderBy.Color
            ) {
                onOrderChange(OrderBy.Date(OrderType.Ascending))
            }
            AppRadioButton(
                modifier = modifier,
                text = "Color",
                selected = orderBy is OrderBy.Color
            ) {
                onOrderChange(OrderBy.Color(OrderType.Ascending))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppRadioButton(
                modifier = modifier,
                text = "Ascending",
                selected = orderBy is OrderBy.Title
            ) {
                onOrderChange(orderBy.copy(OrderType.Ascending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            AppRadioButton(
                modifier = modifier,
                text = "Descending",
                selected = orderBy is OrderBy.Color
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

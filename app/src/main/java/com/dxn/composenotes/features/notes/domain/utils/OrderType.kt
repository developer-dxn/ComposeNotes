package com.dxn.composenotes.features.notes.domain.utils

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}

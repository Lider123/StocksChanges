package ru.babaets.stockchanges.domain.model

data class Stock(
    val id: Long,
    val name: String,
    val price: Float,
    val oldPrice: Float
)

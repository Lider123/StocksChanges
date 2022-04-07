package ru.babaets.stockchanges.data

import ru.babaets.stockchanges.domain.model.Stock

interface Api {

    suspend fun getTopStocks(): List<Stock>
}

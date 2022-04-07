package ru.babaets.stockchanges.domain.interactor

import ru.babaets.stockchanges.domain.model.Stock

interface GetTopStocksUseCase {

    suspend fun execute(): List<Stock>
}

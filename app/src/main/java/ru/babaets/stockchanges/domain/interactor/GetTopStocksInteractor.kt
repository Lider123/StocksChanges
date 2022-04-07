package ru.babaets.stockchanges.domain.interactor

import ru.babaets.stockchanges.data.Api
import ru.babaets.stockchanges.domain.model.Stock

class GetTopStocksInteractor(
    private val api: Api
) : GetTopStocksUseCase {

    override suspend fun execute(): List<Stock> = api.getTopStocks()
}

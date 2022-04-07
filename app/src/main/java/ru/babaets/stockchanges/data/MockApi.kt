package ru.babaets.stockchanges.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.babaets.stockchanges.domain.model.Stock
import kotlin.random.Random

class MockApi : Api {

    private val stocks = mutableListOf<Stock>()

    private val randomPriceChange: Float
        get() = MIN_RANDOM_CHANGE + Random.nextFloat() * (MAX_RANDOM_CHANGE - MIN_RANDOM_CHANGE)

    override suspend fun getTopStocks(): List<Stock> = withContext(Dispatchers.IO) {
        if (stocks.isEmpty()) initStocks() else updateStocks()
        return@withContext stocks.sortedByDescending(Stock::price).take(5)
    }

    private fun initStocks() {
        stocks.clear()
        stocks.addAll(IntRange(1, 10).map(::createStock))
    }

    private fun updateStocks() {
        for (i in stocks.indices) {
            val stock = stocks.removeAt(i)
            stocks.add(i, stock.copy(
                oldPrice = stock.price,
                price = stock.price + randomPriceChange
            ))
        }
    }

    private fun createStock(id: Int) =
        Stock(
            id = id.toLong(),
            name = "Stock${id}",
            price = 100f,
            oldPrice = 100f
        )

    companion object {
        private const val MIN_RANDOM_CHANGE = -5f
        private const val MAX_RANDOM_CHANGE = 5f
    }
}

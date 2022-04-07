package ru.babaets.stockchanges.presentation

import androidx.lifecycle.LiveData
import ru.babaets.stockchanges.domain.model.Stock

interface MainViewModel {

    val stocksLiveData: LiveData<List<Stock>>

    fun onRefreshPressed()
}

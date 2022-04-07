package ru.babaets.stockchanges.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.babaets.stockchanges.domain.interactor.GetTopStocksUseCase
import ru.babaets.stockchanges.domain.model.Stock
import kotlin.coroutines.CoroutineContext

class MainViewModelImpl(
    private val getTopStocksUseCase: GetTopStocksUseCase
) : ViewModel(), MainViewModel, CoroutineScope {

    override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext

    override val stocksLiveData = MutableLiveData<List<Stock>>()

    init {
        loadData()
    }

    override fun onRefreshPressed() {
        loadData()
    }

    private fun loadData() {
        launch {
            stocksLiveData.postValue(getTopStocksUseCase.execute())
        }
    }

    class Factory(
        private val getTopStocksUseCase: GetTopStocksUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            modelClass.getConstructor(GetTopStocksUseCase::class.java)
                .newInstance(getTopStocksUseCase)
    }
}

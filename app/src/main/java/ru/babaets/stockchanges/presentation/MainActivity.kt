package ru.babaets.stockchanges.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import ru.babaets.stockchanges.R
import ru.babaets.stockchanges.data.MockApi
import ru.babaets.stockchanges.domain.model.Stock
import ru.babaets.stockchanges.databinding.ActivityMainBinding
import ru.babaets.stockchanges.domain.interactor.GetTopStocksInteractor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl> {
        MainViewModelImpl.Factory(GetTopStocksInteractor(MockApi()))
    }

    private val adapter: StocksAdapter by lazy {
        StocksAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStocks.adapter = adapter

        viewModel.stocksLiveData.observe(this, ::populateStocks)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.itemRefresh -> {
                viewModel.onRefreshPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun populateStocks(stocks: List<Stock>) {
        adapter.submitList(stocks)
    }
}

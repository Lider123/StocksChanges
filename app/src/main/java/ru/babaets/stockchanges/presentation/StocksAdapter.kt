package ru.babaets.stockchanges.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.babaets.stockchanges.R
import ru.babaets.stockchanges.color
import ru.babaets.stockchanges.domain.model.Stock
import ru.babaets.stockchanges.databinding.ViewItemStockBinding
import ru.babaets.stockchanges.toCurrency
import ru.babaets.stockchanges.toFormattedString
import java.lang.StringBuilder
import kotlin.math.abs

class StocksAdapter : ListAdapter<Stock, StocksAdapter.ViewHolder>(StocksDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ViewItemStockBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context: Context
            get() = itemView.context

        fun bind(item: Stock) {
            binding.run {
                tvName.text = item.name
                tvPrice.text = item.price.toCurrency()
                val sign = when {
                    item.oldPrice <= item.price -> "+"
                    else -> "-"
                }
                tvPriceChange.text = StringBuilder()
                    .append(sign)
                    .append(abs(item.price - item.oldPrice).toCurrency())
                    .append(" (")
                    .append(sign)
                    .append((100f * abs(item.oldPrice - item.price) / item.oldPrice).toFormattedString(2))
                    .append("%)")
                    .toString()
                tvPriceChange.setTextColor(when {
                    item.oldPrice > item.price -> context.color(R.color.red)
                    item.oldPrice < item.price -> context.color(R.color.green)
                    else -> context.color(R.color.gray)
                })
            }
        }
    }

    private class StocksDiffUtilCallback: DiffUtil.ItemCallback<Stock>() {

        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem == newItem
    }
}

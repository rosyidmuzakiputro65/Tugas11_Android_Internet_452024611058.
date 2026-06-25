package com.example.internet

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internet.databinding.ItemColorBinding
import com.example.internet.databinding.ItemHeaderBinding
import com.example.internet.databinding.ItemNumberBinding
import com.example.internet.databinding.ItemProductBinding

class AdvancedAdapter : ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NUMBER = 1
        const val TYPE_COLOR = 2
        const val TYPE_PRODUCT = 3

        private val DiffCallback = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.Header -> TYPE_HEADER
            is ListItem.NumberItem -> TYPE_NUMBER
            is ListItem.ColorItem -> TYPE_COLOR
            is ListItem.ProductItem -> TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(ItemHeaderBinding.inflate(layoutInflater, parent, false))
            TYPE_NUMBER -> NumberViewHolder(ItemNumberBinding.inflate(layoutInflater, parent, false))
            TYPE_COLOR -> ColorViewHolder(ItemColorBinding.inflate(layoutInflater, parent, false))
            TYPE_PRODUCT -> ProductViewHolder(ItemProductBinding.inflate(layoutInflater, parent, false))
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as ListItem.Header)
            is NumberViewHolder -> holder.bind(item as ListItem.NumberItem)
            is ColorViewHolder -> holder.bind(item as ListItem.ColorItem)
            is ProductViewHolder -> holder.bind(item as ListItem.ProductItem)
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem.Header) {
            binding.headerTitle.text = item.title
        }
    }

    class NumberViewHolder(private val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem.NumberItem) {
            binding.numberText.text = item.number.toString()
        }
    }

    class ColorViewHolder(private val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem.ColorItem) {
            binding.colorName.text = item.name
            binding.colorBox.setBackgroundColor(Color.parseColor(item.hex))
        }
    }

    class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem.ProductItem) {
            binding.product = item.product
            binding.executePendingBindings()
        }
    }
}

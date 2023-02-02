package com.example.pocketnutri.ui.fragments.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.databinding.CalculatorRecyclerItemBinding
import com.example.pocketnutri.ui.models.HistoryItem

class CalculatorAdapter(
    private val listener: Listener
) : ListAdapter<HistoryItem, CalculatorAdapter.MyViewHolder>(COMPARATOR) {

    inner class MyViewHolder(private var binding: CalculatorRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyItem: HistoryItem) {
            binding.apply {
                calcRecyclerItemDate.text = historyItem.date
                calcRecyclerItemAmount.text = historyItem.amount
                calcRecyclerItemDeleteButton.setOnClickListener {
                    listener.onDeleteButtonClick(historyItem.date)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            CalculatorRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface Listener {
        fun onDeleteButtonClick(date: String)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ) = newItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ) = oldItem == newItem
        }
    }
}
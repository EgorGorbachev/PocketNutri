package com.example.pocketnutri.ui.fragments.ingredients_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.data.source.rest.models.ingredient_details.Nutrition
import com.example.pocketnutri.databinding.IngredientDetailsRecyclerItemBinding


class IngredientDetailsAdapter() :
    ListAdapter<Nutrition, IngredientDetailsAdapter.MyViewHolder>(COMPARATOR) {

    inner class MyViewHolder(private var binding: IngredientDetailsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nutrition: Nutrition) {
            binding.apply {
                ingredientDetailsRecyclerItemName.text = nutrition.name
                ingredientDetailsRecyclerItemAmount.text = nutrition.amount.toString()
                ingredientDetailsRecyclerItemPercent.text = nutrition.percentOfDailyNeeds
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientDetailsAdapter.MyViewHolder {
        val binding =
            IngredientDetailsRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: IngredientDetailsAdapter.MyViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Nutrition>() {
            override fun areItemsTheSame(
                oldItem: Nutrition,
                newItem: Nutrition
            ) = newItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Nutrition,
                newItem: Nutrition
            ) = oldItem == newItem
        }
    }
}
package com.example.pocketnutri.ui.fragments.recipes_details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.data.source.rest.models.recipes_details.ExtendedIngredient
import com.example.pocketnutri.databinding.RecipeDetailsIngredientItemBinding
import java.util.*

class RecipeDetailsAdapter() : ListAdapter<ExtendedIngredient, RecipeDetailsAdapter.MyViewHolder>(COMPARATOR) {

    inner class MyViewHolder(private var binding: RecipeDetailsIngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(extendedIngredient: ExtendedIngredient) {
            binding.apply {
                recipeDetailsIngredientRecyclerItemName.text = extendedIngredient.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                recipeDetailsIngredientRecyclerItemOriginal.text = extendedIngredient.original
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            RecipeDetailsIngredientItemBinding.inflate(
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

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ) = newItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ) = oldItem == newItem
        }
    }
}
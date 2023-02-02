package com.example.pocketnutri.ui.fragments.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.data.source.rest.models.ingredients.Ingredient
import com.example.pocketnutri.databinding.IngredientsRecyclerItemBinding
import java.util.*

class IngredientsAdapter(
    private val listener: Listener
) : ListAdapter<Ingredient, IngredientsAdapter.MyViewHolder>(COMPARATOR) {

    inner class MyViewHolder(private var binding: IngredientsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.apply {
                ingredientsRecyclerItemTitle.text = ingredient.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                listener.setImage(ingredient.image, ingredientsRecyclerItemImage)
                ingredientsRecyclerItemMoreInfoButton.setOnClickListener {
                    listener.onDetailsButtonClick(ingredient.id)
                }
                ingredientsRecyclerAddButton.setOnClickListener{
                    listener.onAddButtonClick(ingredient.id)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            IngredientsRecyclerItemBinding.inflate(
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
        fun onDetailsButtonClick(id:String)
        fun setImage(image:String, view: ImageView)
        fun onAddButtonClick(id:String)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient
            ) = newItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient
            ) = oldItem == newItem
        }
    }
}
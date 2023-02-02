package com.example.pocketnutri.ui.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.data.source.rest.models.recipes.Recipe
import com.example.pocketnutri.data.source.rest.models.recipes.RecipesResponse
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import com.example.pocketnutri.databinding.RecipesRecyclerItemBinding
import java.util.*

class RecipesAdapter(
    private val listener: Listener
) : ListAdapter<Recipe, RecipesAdapter.MyViewHolder>(COMPARATOR) {

    inner class MyViewHolder(private var binding: RecipesRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.apply {
                recipesRecyclerItemTitle.text = recipe.title.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                listener.setImage(recipe.image, recipesRecyclerItemImage)
                recipesRecyclerItemMoreInfoButton.setOnClickListener {
                    listener.onDetailsButtonClick(recipe.id.toString())
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            RecipesRecyclerItemBinding.inflate(
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
        fun onDetailsButtonClick(id: String)
        fun setImage(image: String, view: ImageView)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ) = newItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ) = oldItem == newItem
        }
    }
}
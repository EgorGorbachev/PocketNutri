package com.example.pocketnutri.ui.fragments.recipes_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketnutri.R
import com.example.pocketnutri.data.source.rest.models.recipes_details.RecipeInfo
import com.example.pocketnutri.databinding.FragmentRecipesDetailsBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class RecipeDetails : BaseFragment(R.layout.fragment_recipes_details) {

    private val binding: FragmentRecipesDetailsBinding by viewBinding()
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private val args: RecipeDetailsArgs by navArgs()
    private lateinit var info: RecipeInfo
    private var adapter = RecipeDetailsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfo()
    }

    private fun getInfo() {
        CoroutineScope(lifecycleScope.coroutineContext).launch {
            info = viewModel.getRecipeDetails(args.id)
            initUI()
            initAdapter()
        }
    }

    private fun initUI() {
        Glide.with(requireContext()).load(info.image).into(binding.recipeDetailsImage)
        binding.apply {
            recipeDetailsTitle.text = info.title
            recipeDetailsTime.text = info.readyInMinutes.toString()
            if (info.dishTypes.isNotEmpty()) {
                recipeDetailsMealType.text = info.dishTypes[0].replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
        } else recipeDetailsMealType.text = "~No info~"
            ingredientDetailsSummary.text =
                info.summary
                    .replace("<b>", "")
                    .replace("</b>", "")
                    .replace("<a href=", "")
                    .replace(">", "")
                    .replace("</a>", "")
                    .replace("</a", "")
        }
    }

    private fun initAdapter() {
        val recyclerView: RecyclerView =
            requireView().findViewById(R.id.ingredientDetailsRecyclerview)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.submitList(info.extendedIngredients)
    }
}
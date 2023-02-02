package com.example.pocketnutri.ui.fragments.ingredients_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketnutri.R
import com.example.pocketnutri.data.source.rest.models.ingredient_details.IngredientInfo
import com.example.pocketnutri.databinding.FragmentIngredientsDetailsBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import com.example.pocketnutri.R.id.action_ingredientsDetails_to_ingredients as toIngredients

@AndroidEntryPoint
class IngredientDetails : BaseFragment(R.layout.fragment_ingredients_details), View.OnClickListener {

    private val binding: FragmentIngredientsDetailsBinding by viewBinding()
    private val viewModel: IngredientDetailsViewModel by viewModels()
    private val args: IngredientDetailsArgs by navArgs()
    private var adapter = IngredientDetailsAdapter()
    private lateinit var info:IngredientInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initAdapter()
        initListeners()
    }

    private fun initUi() {
        CoroutineScope(lifecycleScope.coroutineContext).launch(Dispatchers.Main) {
            info = viewModel.getIngredientDetails(args.id)
            binding.apply {
                ingredientsDetailsTitle.text =
                    info.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                Glide.with(requireView())
                    .load(URL("https://spoonacular.com/cdn/ingredients_250x250/${info.image}"))
                    .into(ingredientsDetailsImage)
                try {
                    ingredientsDetailsCaloricBreakdownProteinAmount.text =
                        info.nutrition.caloricBreakdown.percentProtein.toString()
                    ingredientsDetailsCaloricBreakdownCarbsAmount.text =
                        info.nutrition.caloricBreakdown.percentCarbs.toString()
                    ingredientsDetailsCaloricBreakdownFatAmount.text =
                        info.nutrition.caloricBreakdown.percentFat.toString()
                } catch (e: Exception) {
                    Log.e("error", e.toString())
                }
                val calories = info.nutrition.nutrients.find  { it.name.startsWith("Calories") }
                ingredientsDetailsCaloriesAmount.text = calories?.amount.toString()
            }
            adapter.submitList(info.nutrition.nutrients)
        }
    }

    private fun initAdapter() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.ingredientDetailsRecyclerview)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun initListeners() {
        binding.ingredientsDetailsButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.ingredientsDetailsButton -> addCalories()
        }
    }

    private fun addCalories(){
        val calories = info.nutrition.nutrients
            .find { it.name.startsWith("Calories") }?.amount
        val currentCalories =
            if (viewModel.getPrefsString() == "") 0.0
            else viewModel.getPrefsString().toDouble()
        val newCalories = calories!!.plus(currentCalories)
        viewModel.setPrefsString(newCalories.toString())
        navigate(toIngredients)
    }

}
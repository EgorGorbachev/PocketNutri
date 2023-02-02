package com.example.pocketnutri.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketnutri.R
import com.example.pocketnutri.databinding.FragmentRecipesBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.example.pocketnutri.ui.fragments.ingredients.IngredientsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.pocketnutri.R.id.action_recipes_to_calculator as toCalculator
import com.example.pocketnutri.R.id.action_recipes_to_ingredients as toIngredients

@AndroidEntryPoint
class Recipes : BaseFragment(R.layout.fragment_recipes),
    View.OnClickListener,
    RecipesAdapter.Listener,
    TextView.OnEditorActionListener {
    private val binding: FragmentRecipesBinding by viewBinding()
    private val viewModel: RecipesViewModel by viewModels()
    private var adapter = RecipesAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapter()
    }

    private fun initListeners() {
        binding.fromRecToIngredientsButton.setOnClickListener(this)
        binding.fromRecToCalculatorButton.setOnClickListener(this)
        binding.recEditText.setOnEditorActionListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fromRecToIngredientsButton -> navigate(toIngredients)
            R.id.fromRecToCalculatorButton -> navigate(toCalculator)
        }
    }

    private fun initAdapter() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recRecycler)
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

    override fun onDetailsButtonClick(id: String) {
        navigate(RecipesDirections.actionRecipesToRecipesDetails(id))
    }

    override fun setImage(image: String, view: ImageView) {
        Glide.with(requireContext()).load(image).into(view)
    }

    override fun onEditorAction(textView: TextView, i: Int, p2: KeyEvent?): Boolean {
        return if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_NEXT) {
            CoroutineScope(lifecycleScope.coroutineContext).launch {
                try {
                    if (viewModel.getRecipes(textView.text.toString()).results.isNotEmpty()) {
                        adapter.submitList(viewModel.getRecipes(textView.text.toString()).results)
                        binding.recEmptyInputMessage.isVisible = false
                        binding.recIncorrectRequestMessage.isVisible = false
                    } else {
                        binding.recEmptyInputMessage.isVisible = false
                        binding.recIncorrectRequestMessage.isVisible = true
                    }
                } catch (e: Exception) {
                    Log.e("error", e.toString())
                }
            }
            true
        } else {
            false
        }
    }
}
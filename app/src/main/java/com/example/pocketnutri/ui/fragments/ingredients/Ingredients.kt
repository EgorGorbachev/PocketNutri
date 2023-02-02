package com.example.pocketnutri.ui.fragments.ingredients

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketnutri.R
import com.example.pocketnutri.databinding.FragmentIngredientsBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.example.pocketnutri.ui.models.HistoryItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt
import com.example.pocketnutri.R.id.action_ingredients_to_calculator as toCalculator
import com.example.pocketnutri.R.id.action_ingredients_to_recipes as toRecipes

@AndroidEntryPoint
class Ingredients :
    BaseFragment(R.layout.fragment_ingredients),
    View.OnClickListener,
    TextView.OnEditorActionListener,
    IngredientsAdapter.Listener {
    private val binding: FragmentIngredientsBinding by viewBinding()
    private val viewModel: IngredientsViewModel by viewModels()
    private var adapter = IngredientsAdapter(this)
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initRecycler()
        initUi()
    }

    private fun initListeners() {
        binding.fromIngToRecipesButton.setOnClickListener(this)
        binding.fromIngToCalculatorButton.setOnClickListener(this)
        binding.ingredientsEditText.setOnEditorActionListener(this)
        binding.ingredientsAddToHistoryButton.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.fromIngToRecipesButton -> navigate(toRecipes)
            R.id.fromIngToCalculatorButton -> navigate(toCalculator)
            R.id.ingredientsAddToHistoryButton -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addToHistory()
            }
        }
    }

    private fun initRecycler() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.ingredientsRecyclerview)
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

    override fun onEditorAction(textView: TextView, i: Int, p2: KeyEvent?): Boolean {
        return if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_NEXT) {
            CoroutineScope(lifecycleScope.coroutineContext).launch {
                try {
                    if (viewModel.getIngredients(textView.text.toString()).results.isNotEmpty()) {
                        adapter.submitList(viewModel.getIngredients(textView.text.toString()).results)
                        binding.ingredientsEmptyInputMessage.isVisible = false
                        binding.ingredientsIncorrectRequestMessage.isVisible = false
                    } else {
                        binding.ingredientsEmptyInputMessage.isVisible = false
                        binding.ingredientsIncorrectRequestMessage.isVisible = true
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

    override fun onDetailsButtonClick(id: String) {
        navigate(IngredientsDirections.actionIngredientsToIngredientsDetails(id))
    }

    override fun setImage(image: String, view: ImageView) {
        Glide.with(this).load(URL("https://spoonacular.com/cdn/ingredients_250x250/$image"))
            .into(view)
    }

    override fun onAddButtonClick(id: String) {
        CoroutineScope(lifecycleScope.coroutineContext).launch {
            val info = viewModel.getIngredientInfo(id).nutrition.nutrients
            val calories = info.find { it.name.startsWith("Calories") }?.amount
            val currentCalories =
                if (viewModel.getPrefsString() == "") 0.0
                else viewModel.getPrefsString().toDouble()
            val newCalories = calories!!.plus(currentCalories)
            viewModel.setPrefsString(newCalories.toString())
            initUi()
        }
    }

    private fun initUi() {
        val caloriesAmount = viewModel.getPrefsString()
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.DOWN
        binding.ingredientsAddToHistoryButtonCaloriesAmount.text =
            if (caloriesAmount == "") "0"
            else df.format(caloriesAmount.toDouble())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addToHistory() {
        val value = binding.ingredientsAddToHistoryButtonCaloriesAmount.text
        if (value != "0") {
            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            val current = LocalDateTime.now()
            val formatterNormal = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formattedNormal = current.format(formatterNormal)
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(currentFirebaseUser!!.uid)
                .child(formattedNormal.replace(" ", "").replace(".", ""))
                .setValue(HistoryItem(formattedNormal, value.toString()))
            viewModel.setPrefsString("")
            initUi()
        }
    }
}
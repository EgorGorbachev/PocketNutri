package com.example.pocketnutri.ui.fragments.calculator

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketnutri.R
import com.example.pocketnutri.databinding.FragmentCalculatorBinding
import com.example.pocketnutri.ui.common.base.BaseFragment
import com.example.pocketnutri.ui.delegates.viewBinding
import com.example.pocketnutri.ui.fragments.ingredients.IngredientsAdapter
import com.example.pocketnutri.ui.models.HistoryItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import com.example.pocketnutri.R.id.action_calculator_to_ingredients as toIngredients
import com.example.pocketnutri.R.id.action_calculator_to_recipes as toRecipes
import com.example.pocketnutri.R.id.action_calculator_to_settings as toSettings


@AndroidEntryPoint
class Calculator : BaseFragment(R.layout.fragment_calculator),
    View.OnClickListener,
    CalculatorAdapter.Listener
{
    private val binding: FragmentCalculatorBinding by viewBinding()
    private val viewModel: CalculatorViewModel by viewModels()
    private var adapter = CalculatorAdapter(this)
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapter()
    }

    private fun initListeners() {
        binding.fromCalcToIngredientsButton.setOnClickListener(this)
        binding.fromCalcToRecipesButton.setOnClickListener(this)
        binding.calcSettingsButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fromCalcToIngredientsButton -> navigate(toIngredients)
            R.id.fromCalcToRecipesButton -> navigate(toRecipes)
            R.id.calcSettingsButton -> navigate(toSettings)
        }
    }

    private fun initAdapter() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.calcRecycler)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        submitHistoryList()
    }

    private fun submitHistoryList() {
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(currentFirebaseUser!!.uid).get().addOnCompleteListener{
            if (it.isSuccessful){
                val list = it.result.children.mapNotNull { doc ->
                    doc.getValue(HistoryItem::class.java)
                }
                if (list.isNotEmpty()) {
                    binding.calcEmptyHistoryMessage.isVisible = false
                    adapter.submitList(list)
                }
            } else Log.d(TAG, it.exception?.message.toString())
        }
    }

    override fun onDeleteButtonClick(date: String) {
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(currentFirebaseUser!!.uid)
            .child(date.replace(" ", "").replace(".", ""))
            .removeValue()
        submitHistoryList()
    }
}
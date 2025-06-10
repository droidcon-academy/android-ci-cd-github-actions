package com.droidcon.expenselogger.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidcon.expenselogger.data.database.ExpenseDatabase
import com.droidcon.expenselogger.data.repository.ExpenseRepository
import com.droidcon.expenselogger.domain.usecase.AddExpenseUseCase
import com.droidcon.expenselogger.domain.usecase.GetMonthlyOverviewUseCase

class ExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            val database = ExpenseDatabase.getDatabase(context)
            val repository = ExpenseRepository(database.expenseDao())
            val addExpenseUseCase = AddExpenseUseCase(repository)
            val getMonthlyOverviewUseCase = GetMonthlyOverviewUseCase(repository)

            return ExpenseViewModel(
                addExpenseUseCase = addExpenseUseCase,
                getMonthlyOverviewUseCase = getMonthlyOverviewUseCase,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
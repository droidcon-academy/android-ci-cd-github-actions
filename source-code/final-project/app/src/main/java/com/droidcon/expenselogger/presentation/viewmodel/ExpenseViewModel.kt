package com.droidcon.expenselogger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.expenselogger.data.model.Expense
import com.droidcon.expenselogger.domain.usecase.AddExpenseUseCase
import com.droidcon.expenselogger.domain.usecase.GetMonthlyOverviewUseCase
import com.droidcon.expenselogger.data.repository.ExpenseRepository
import com.droidcon.expenselogger.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getMonthlyOverviewUseCase: GetMonthlyOverviewUseCase,
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    private val _addExpenseResult = MutableSharedFlow<AddExpenseResult>()
    val addExpenseResult: SharedFlow<AddExpenseResult> = _addExpenseResult.asSharedFlow()

    val monthlyOverview = getMonthlyOverviewUseCase.getCurrentMonthOverview()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GetMonthlyOverviewUseCase.MonthlyOverview(
                month = DateUtils.getCurrentMonth(),
                totalAmount = 0.0,
                totalExpenses = 0,
                expenses = emptyList(),
                categorySummary = emptyMap()
            )
        )

    val allExpenses = repository.getAllExpenses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addExpense(
        title: String,
        amount: Double,
        category: String,
        description: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val expense = Expense(
                title = title,
                amount = amount,
                category = category,
                description = description,
                date = System.currentTimeMillis()
            )

            when (val result = addExpenseUseCase.execute(expense)) {
                is AddExpenseUseCase.Result.Success -> {
                    _addExpenseResult.emit(AddExpenseResult.Success)
                }

                is AddExpenseUseCase.Result.DailyLimitReached -> {
                    _addExpenseResult.emit(AddExpenseResult.DailyLimitReached)
                }

                is AddExpenseUseCase.Result.Error -> {
                    _addExpenseResult.emit(AddExpenseResult.Error(result.message))
                }
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ExpenseUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class AddExpenseResult {
    object Success : AddExpenseResult()
    object DailyLimitReached : AddExpenseResult()
    data class Error(val message: String) : AddExpenseResult()
}
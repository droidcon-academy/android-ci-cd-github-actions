package com.droidcon.expenselogger.domain.usecase

import com.droidcon.expenselogger.data.model.Expense
import com.droidcon.expenselogger.data.repository.ExpenseRepository
import com.droidcon.expenselogger.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlyOverviewUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    data class MonthlyOverview(
        val month: String,
        val totalAmount: Double,
        val totalExpenses: Int,
        val expenses: List<Expense>,
        val categorySummary: Map<String, Double>
    )

    fun execute(month: Long): Flow<MonthlyOverview> {
        val startOfMonth = DateUtils.getStartOfMonth(month)
        val endOfMonth = DateUtils.getEndOfMonth(month)

        return repository.getExpensesByDateRange(startOfMonth, endOfMonth)
            .map { expenses ->
                val totalAmount = expenses.sumOf { it.amount }
                val categorySummary = expenses.groupBy { it.category }
                    .mapValues { (_, expenseList) -> expenseList.sumOf { it.amount } }

                MonthlyOverview(
                    month = DateUtils.formatMonth(month),
                    totalAmount = totalAmount,
                    totalExpenses = expenses.size,
                    expenses = expenses,
                    categorySummary = categorySummary
                )
            }
    }

    fun getCurrentMonthOverview(): Flow<MonthlyOverview> {
        return execute(System.currentTimeMillis())
    }
}
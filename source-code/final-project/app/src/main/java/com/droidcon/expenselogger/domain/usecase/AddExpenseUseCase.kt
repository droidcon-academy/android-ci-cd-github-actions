package com.droidcon.expenselogger.domain.usecase

import com.droidcon.expenselogger.data.model.Expense
import com.droidcon.expenselogger.data.repository.ExpenseRepository
import com.droidcon.expenselogger.util.BuildConfigHelper
import com.droidcon.expenselogger.util.DateUtils
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {

    sealed class Result {
        data class Success(val expenseId: Long) : Result()
        object DailyLimitReached : Result()
        data class Error(val message: String) : Result()
    }

    suspend fun execute(expense: Expense): Result {
        return try {
            // Check daily limit for free version
            if (!BuildConfigHelper.isPaidVersion) {
                val today = DateUtils.getStartOfDay(System.currentTimeMillis())
                val tomorrow = DateUtils.getEndOfDay(System.currentTimeMillis())
                val todayCount = repository.getExpenseCountByDateRange(today, tomorrow)

                if (!BuildConfigHelper.canAddMoreEntries(todayCount)) {
                    return Result.DailyLimitReached
                }
            }

            val expenseId = repository.insertExpense(expense)
            Result.Success(expenseId)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error occurred")
        }
    }
}
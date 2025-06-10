package com.droidcon.expenselogger.data.repository

import com.droidcon.expenselogger.data.dao.ExpenseDao
import com.droidcon.expenselogger.data.model.Expense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)

    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>> =
        expenseDao.getExpensesByDateRange(startDate, endDate)

    fun getExpensesByCategory(category: String): Flow<List<Expense>> =
        expenseDao.getExpensesByCategory(category)

    suspend fun getTotalAmountByDateRange(startDate: Long, endDate: Long): Double =
        expenseDao.getTotalAmountByDateRange(startDate, endDate) ?: 0.0

    suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Int =
        expenseDao.getExpenseCountByDateRange(startDate, endDate)

    suspend fun insertExpense(expense: Expense): Long = expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)

    suspend fun deleteExpenseById(id: Long) = expenseDao.deleteExpenseById(id)

    suspend fun deleteAllExpenses() = expenseDao.deleteAllExpenses()
}
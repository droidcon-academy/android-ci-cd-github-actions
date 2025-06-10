package com.droidcon.expenselogger.data.repository

import com.droidcon.expenselogger.data.dao.ExpenseDao
import com.droidcon.expenselogger.data.model.Expense
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExpenseRepositoryTest {

    private lateinit var expenseDao: ExpenseDao
    private lateinit var repository: ExpenseRepository

    @Before
    fun setUp() {
        expenseDao = mockk(relaxed = true) // relaxed = true to avoid mocking all funcs
        repository = ExpenseRepository(expenseDao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createDummyExpense(
        id: Long,
        amount: Double = 10.0,
        category: String = "Test",
        date: Long = System.currentTimeMillis()
    ) = Expense(
        id = id,
        title = "Expense $id",
        amount = amount,
        category = category,
        description = null,
        date = date,
        createdAt = date,
        updatedAt = date
    )

    @Test
    fun `getAllExpenses should return flow from DAO`() = runTest {
        // Given
        val expenses = listOf(createDummyExpense(1), createDummyExpense(2))
        every { expenseDao.getAllExpenses() } returns flowOf(expenses)

        // When
        val result = repository.getAllExpenses().first()

        // Then
        assertEquals(expenses, result)
        verify { expenseDao.getAllExpenses() }
    }

    @Test
    fun `getExpenseById should call DAO and return expense`() = runTest {
        // Given
        val expense = createDummyExpense(1)
        coEvery { expenseDao.getExpenseById(1L) } returns expense

        // When
        val result = repository.getExpenseById(1L)

        // Then
        assertEquals(expense, result)
        coVerify { expenseDao.getExpenseById(1L) }
    }

    @Test
    fun `getExpenseById should return null if DAO returns null`() = runTest {
        // Given
        coEvery { expenseDao.getExpenseById(1L) } returns null

        // When
        val result = repository.getExpenseById(1L)

        // Then
        assertNull(result)
        coVerify { expenseDao.getExpenseById(1L) }
    }

    @Test
    fun `getExpensesByDateRange should return flow from DAO`() = runTest {
        // Given
        val startDate = 1000L
        val endDate = 2000L
        val expenses = listOf(createDummyExpense(1, date = 1500L))
        every { expenseDao.getExpensesByDateRange(startDate, endDate) } returns flowOf(expenses)

        // When
        val result = repository.getExpensesByDateRange(startDate, endDate).first()

        // Then
        assertEquals(expenses, result)
        verify { expenseDao.getExpensesByDateRange(startDate, endDate) }
    }

    @Test
    fun `getExpensesByCategory should return flow from DAO`() = runTest {
        // Given
        val category = "Food"
        val expenses = listOf(createDummyExpense(1, category = "Food"))
        every { expenseDao.getExpensesByCategory(category) } returns flowOf(expenses)

        // When
        val result = repository.getExpensesByCategory(category).first()

        // Then
        assertEquals(expenses, result)
        verify { expenseDao.getExpensesByCategory(category) }
    }

    @Test
    fun `getTotalAmountByDateRange should call DAO and return sum`() = runTest {
        // Given
        val startDate = 1000L
        val endDate = 2000L
        val expectedSum = 150.75
        coEvery { expenseDao.getTotalAmountByDateRange(startDate, endDate) } returns expectedSum

        // When
        val result = repository.getTotalAmountByDateRange(startDate, endDate)

        // Then
        assertEquals(expectedSum, result, 0.001)
        coVerify { expenseDao.getTotalAmountByDateRange(startDate, endDate) }
    }

    @Test
    fun `getTotalAmountByDateRange should return 0 if DAO returns null`() = runTest {
        // Given
        val startDate = 1000L
        val endDate = 2000L
        coEvery { expenseDao.getTotalAmountByDateRange(startDate, endDate) } returns null

        // When
        val result = repository.getTotalAmountByDateRange(startDate, endDate)

        // Then
        assertEquals(0.0, result, 0.001)
        coVerify { expenseDao.getTotalAmountByDateRange(startDate, endDate) }
    }

    @Test
    fun `getExpenseCountByDateRange should call DAO and return count`() = runTest {
        // Given
        val startDate = 1000L
        val endDate = 2000L
        val expectedCount = 5
        coEvery { expenseDao.getExpenseCountByDateRange(startDate, endDate) } returns expectedCount

        // When
        val result = repository.getExpenseCountByDateRange(startDate, endDate)

        // Then
        assertEquals(expectedCount, result)
        coVerify { expenseDao.getExpenseCountByDateRange(startDate, endDate) }
    }

    @Test
    fun `insertExpense should call DAO and return id`() = runTest {
        // Given
        val expenseToInsert = createDummyExpense(0) // ID 0 for new expense
        val expectedId = 1L
        coEvery { expenseDao.insertExpense(expenseToInsert) } returns expectedId

        // When
        val resultId = repository.insertExpense(expenseToInsert)

        // Then
        assertEquals(expectedId, resultId)
        coVerify { expenseDao.insertExpense(expenseToInsert) }
    }

    @Test
    fun `updateExpense should call DAO`() = runTest {
        // Given
        val expenseToUpdate = createDummyExpense(1)
        coEvery { expenseDao.updateExpense(expenseToUpdate) } just Runs // or returns Unit

        // When
        repository.updateExpense(expenseToUpdate)

        // Then
        coVerify { expenseDao.updateExpense(expenseToUpdate) }
    }

    @Test
    fun `deleteExpense should call DAO`() = runTest {
        // Given
        val expenseToDelete = createDummyExpense(1)
        coEvery { expenseDao.deleteExpense(expenseToDelete) } just Runs

        // When
        repository.deleteExpense(expenseToDelete)

        // Then
        coVerify { expenseDao.deleteExpense(expenseToDelete) }
    }

    @Test
    fun `deleteExpenseById should call DAO`() = runTest {
        // Given
        val expenseIdToDelete = 1L
        coEvery { expenseDao.deleteExpenseById(expenseIdToDelete) } just Runs

        // When
        repository.deleteExpenseById(expenseIdToDelete)

        // Then
        coVerify { expenseDao.deleteExpenseById(expenseIdToDelete) }
    }

    @Test
    fun `deleteAllExpenses should call DAO`() = runTest {
        // Given
        coEvery { expenseDao.deleteAllExpenses() } just Runs

        // When
        repository.deleteAllExpenses()

        // Then
        coVerify { expenseDao.deleteAllExpenses() }
    }
}

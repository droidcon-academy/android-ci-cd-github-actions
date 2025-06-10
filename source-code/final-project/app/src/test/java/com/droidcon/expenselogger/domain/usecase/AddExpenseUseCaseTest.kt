package com.droidcon.expenselogger.domain.usecase

import com.droidcon.expenselogger.data.model.Expense
import com.droidcon.expenselogger.data.repository.ExpenseRepository
import com.droidcon.expenselogger.util.DateUtils
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddExpenseUseCaseTest {

    private lateinit var repository: ExpenseRepository
    private lateinit var addExpenseUseCase: AddExpenseUseCase

    @Before
    fun setUp() {
        repository = mockk()
        addExpenseUseCase = AddExpenseUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `execute should return Success when expense is added successfully`() = runTest {
        // Given
        val expense = Expense(
            title = "Test Expense",
            amount = 10.0,
            category = "Food",
            date = System.currentTimeMillis()
        )
        val expectedId = 1L

        coEvery { repository.getExpenseCountByDateRange(any(), any()) } returns 5
        coEvery { repository.insertExpense(expense) } returns expectedId

        // When
        val result = addExpenseUseCase.execute(expense)

        // Then
        assertTrue(result is AddExpenseUseCase.Result.Success)
        assertEquals(expectedId, (result as AddExpenseUseCase.Result.Success).expenseId)
        coVerify { repository.insertExpense(expense) }
    }

    @Test
    fun `execute should return DailyLimitReached when free version limit is exceeded`() = runTest {
        // Given
        val expense = Expense(
            title = "Test Expense",
            amount = 10.0,
            category = "Food",
            date = System.currentTimeMillis()
        )

        // Mock the BuildConfigHelper to simulate free version
        mockkObject(com.droidcon.expenselogger.util.BuildConfigHelper)
        every { com.droidcon.expenselogger.util.BuildConfigHelper.isPaidVersion } returns false
        every { com.droidcon.expenselogger.util.BuildConfigHelper.canAddMoreEntries(10) } returns false

        coEvery { repository.getExpenseCountByDateRange(any(), any()) } returns 10

        // When
        val result = addExpenseUseCase.execute(expense)

        // Then
        assertTrue(result is AddExpenseUseCase.Result.DailyLimitReached)
        coVerify(exactly = 0) { repository.insertExpense(any()) }
    }

    @Test
    fun `execute should return Error when repository throws exception`() = runTest {
        // Given
        val expense = Expense(
            title = "Test Expense",
            amount = 10.0,
            category = "Food",
            date = System.currentTimeMillis()
        )
        val errorMessage = "Database error"

        coEvery { repository.getExpenseCountByDateRange(any(), any()) } returns 5
        coEvery { repository.insertExpense(expense) } throws RuntimeException(errorMessage)

        // When
        val result = addExpenseUseCase.execute(expense)

        // Then
        assertTrue(result is AddExpenseUseCase.Result.Error)
        assertEquals(errorMessage, (result as AddExpenseUseCase.Result.Error).message)
    }

    @Test
    fun `execute should allow unlimited entries for paid version`() = runTest {
        // Given
        val expense = Expense(
            title = "Test Expense",
            amount = 10.0,
            category = "Food",
            date = System.currentTimeMillis()
        )
        val expectedId = 1L

        // Mock the BuildConfigHelper to simulate paid version
        mockkObject(com.droidcon.expenselogger.util.BuildConfigHelper)
        every { com.droidcon.expenselogger.util.BuildConfigHelper.isPaidVersion } returns true

        coEvery { repository.insertExpense(expense) } returns expectedId

        // When
        val result = addExpenseUseCase.execute(expense)

        // Then
        assertTrue(result is AddExpenseUseCase.Result.Success)
        assertEquals(expectedId, (result as AddExpenseUseCase.Result.Success).expenseId)
        coVerify(exactly = 0) { repository.getExpenseCountByDateRange(any(), any()) }
        coVerify { repository.insertExpense(expense) }
    }
}
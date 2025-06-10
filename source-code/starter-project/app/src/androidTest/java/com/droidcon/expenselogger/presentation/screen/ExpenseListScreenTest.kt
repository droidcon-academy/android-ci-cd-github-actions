package com.droidcon.expenselogger.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.droidcon.expenselogger.ui.theme.ExpenseLoggerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpenseListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun expenseListScreen_displaysTitle() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Expense Logger")
            .assertIsDisplayed()
    }

    @Test
    fun expenseListScreen_displaysFloatingActionButton() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Add Expense")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun expenseListScreen_displaysMonthlyOverviewButton() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Monthly Overview")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun expenseListScreen_displaysThisMonthCard() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("This Month")
            .assertIsDisplayed()
    }

    @Test
    fun expenseListScreen_displaysEmptyStateWhenNoExpenses() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("No expenses yet")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Tap + to add your first expense")
            .assertIsDisplayed()
    }

    @Test
    fun expenseListScreen_fabClickTriggersNavigation() {
        var navigationTriggered = false

        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = { navigationTriggered = true },
                    onNavigateToMonthlyOverview = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Add Expense")
            .performClick()

        assert(navigationTriggered)
    }

    @Test
    fun expenseListScreen_monthlyOverviewClickTriggersNavigation() {
        var navigationTriggered = false

        composeTestRule.setContent {
            ExpenseLoggerTheme {
                ExpenseListScreen(
                    onNavigateToAddExpense = {},
                    onNavigateToMonthlyOverview = { navigationTriggered = true }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Monthly Overview")
            .performClick()

        assert(navigationTriggered)
    }
} 
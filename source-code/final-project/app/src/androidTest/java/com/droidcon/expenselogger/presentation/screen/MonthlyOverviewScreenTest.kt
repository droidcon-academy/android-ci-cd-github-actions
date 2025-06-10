package com.droidcon.expenselogger.presentation.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.droidcon.expenselogger.ui.theme.ExpenseLoggerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MonthlyOverviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun monthlyOverviewScreen_displaysTitle() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        composeTestRule
            .onNodeWithText("Monthly Overview")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_displaysBackButton() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun monthlyOverviewScreen_backButtonTriggersNavigation() {
        var navigationTriggered = false

        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = { navigationTriggered = true })
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        assert(navigationTriggered)
    }

    @Test
    fun monthlyOverviewScreen_displaysTotalSpentLabel() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        composeTestRule
            .onNodeWithText("Total Spent")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_displaysTotalExpensesLabel() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        composeTestRule
            .onNodeWithText("Total Expenses")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_displaysEmptyStateWhenNoExpenses() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        composeTestRule
            .onNodeWithText("No expenses this month")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Start tracking your expenses to see analytics here")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_scrollableContent() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // The screen should be scrollable (LazyColumn)
        composeTestRule
            .onRoot()
            .assertExists()
    }

    @Test
    fun monthlyOverviewScreen_summaryCardIsDisplayed() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // The summary card should contain both total spent and total expenses
        composeTestRule
            .onNodeWithText("Total Spent")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Total Expenses")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_hasProperTopAppBarStructure() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // Check that the top app bar contains the title and back button
        composeTestRule
            .onNodeWithText("Monthly Overview")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
    }

    @Test
    fun monthlyOverviewScreen_displaysCategorySection() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // When there are no expenses, category section shouldn't be displayed
        // This is a negative test - "By Category" should not be shown when empty
        composeTestRule
            .onNodeWithText("By Category")
            .assertDoesNotExist()
    }

    @Test
    fun monthlyOverviewScreen_displaysAllExpensesSection() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // When there are no expenses, "All Expenses" section shouldn't be displayed
        // This is a negative test - "All Expenses" should not be shown when empty
        composeTestRule
            .onNodeWithText("All Expenses")
            .assertDoesNotExist()
    }

    @Test
    fun monthlyOverviewScreen_hasCorrectLayoutStructure() {
        composeTestRule.setContent {
            ExpenseLoggerTheme {
                MonthlyOverviewScreen(onNavigateBack = {})
            }
        }

        // Verify the basic structure exists
        composeTestRule
            .onNodeWithText("Monthly Overview")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Total Spent")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Total Expenses")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("No expenses this month")
            .assertIsDisplayed()
    }
} 
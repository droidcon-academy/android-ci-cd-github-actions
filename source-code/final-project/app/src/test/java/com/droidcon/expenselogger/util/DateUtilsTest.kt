package com.droidcon.expenselogger.util

import org.junit.Test
import org.junit.Assert.*
import java.util.Calendar

class DateUtilsTest {

    @Test
    fun `getStartOfDay should return timestamp at start of day`() {
        // Given
        val testTimestamp = System.currentTimeMillis()

        // When
        val startOfDay = DateUtils.getStartOfDay(testTimestamp)

        // Then
        val calendar = Calendar.getInstance().apply { timeInMillis = startOfDay }
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    fun `getEndOfDay should return timestamp at end of day`() {
        // Given
        val testTimestamp = System.currentTimeMillis()

        // When
        val endOfDay = DateUtils.getEndOfDay(testTimestamp)

        // Then
        val calendar = Calendar.getInstance().apply { timeInMillis = endOfDay }
        assertEquals(23, calendar.get(Calendar.HOUR_OF_DAY))
        assertEquals(59, calendar.get(Calendar.MINUTE))
        assertEquals(59, calendar.get(Calendar.SECOND))
        assertEquals(999, calendar.get(Calendar.MILLISECOND))
    }

    @Test
    fun `formatDate should return readable date format`() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.DECEMBER, 25, 15, 30, 45)
        }
        val timestamp = calendar.timeInMillis

        // When
        val formattedDate = DateUtils.formatDate(timestamp)

        // Then
        assertTrue(formattedDate.contains("Dec"))
        assertTrue(formattedDate.contains("25"))
        assertTrue(formattedDate.contains("2024"))
    }

    @Test
    fun `getCurrentMonth should return current month string`() {
        // When
        val currentMonth = DateUtils.getCurrentMonth()

        // Then
        assertNotNull(currentMonth)
        assertTrue(currentMonth.contains("-"))
        assertEquals(7, currentMonth.length) // Format: yyyy-MM
    }

    @Test
    fun `getStartOfMonth should return timestamp at start of month`() {
        // Given
        val testTimestamp = System.currentTimeMillis()

        // When
        val startOfMonth = DateUtils.getStartOfMonth(testTimestamp)

        // Then
        val calendar = Calendar.getInstance().apply { timeInMillis = startOfMonth }
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH))
        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, calendar.get(Calendar.MINUTE))
        assertEquals(0, calendar.get(Calendar.SECOND))
        assertEquals(0, calendar.get(Calendar.MILLISECOND))
    }
}
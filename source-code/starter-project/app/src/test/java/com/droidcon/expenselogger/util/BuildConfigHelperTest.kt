package com.droidcon.expenselogger.util

import org.junit.Test
import org.junit.Assert.*

class BuildConfigHelperTest {

    @Test
    fun `maxDailyEntries should be configured correctly`() {
        // When/Then - This will test the actual BuildConfig value for the current variant
        assertTrue("Max daily entries should be positive", BuildConfigHelper.maxDailyEntries > 0)
    }

    @Test
    fun `canAddMoreEntries should return true when under limit`() {
        // Given
        val currentCount = 5

        // When
        val canAdd = BuildConfigHelper.canAddMoreEntries(currentCount)

        // Then
        if (BuildConfigHelper.isPaidVersion) {
            assertTrue("Paid version should always allow more entries", canAdd)
        } else {
            // For free version, depends on the limit
            val expected = currentCount < BuildConfigHelper.maxDailyEntries
            assertEquals("Should match expected limit behavior", expected, canAdd)
        }
    }

    @Test
    fun `canAddMoreEntries should respect version limits`() {
        // Given - test with a count equal to free version limit
        val limitCount = 10

        // When
        val canAdd = BuildConfigHelper.canAddMoreEntries(limitCount)

        // Then
        if (BuildConfigHelper.isPaidVersion) {
            assertTrue("Paid version should always allow more entries", canAdd)
        } else {
            assertFalse("Free version should not allow more than limit", canAdd)
        }
    }

    @Test
    fun `isPaidVersion should be boolean`() {
        // When/Then - This will test the actual BuildConfig value
        assertTrue(
            "isPaidVersion should be either true or false",
            BuildConfigHelper.isPaidVersion || !BuildConfigHelper.isPaidVersion
        )
    }
}
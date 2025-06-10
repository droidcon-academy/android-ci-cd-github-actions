package com.droidcon.expenselogger.util

import com.droidcon.expenselogger.BuildConfig

object BuildConfigHelper {

    val maxDailyEntries: Int = BuildConfig.MAX_DAILY_ENTRIES
    val isPaidVersion: Boolean = BuildConfig.IS_PAID_VERSION

    fun canAddMoreEntries(currentDailyCount: Int): Boolean {
        return if (isPaidVersion) {
            true
        } else {
            currentDailyCount < maxDailyEntries
        }
    }
}
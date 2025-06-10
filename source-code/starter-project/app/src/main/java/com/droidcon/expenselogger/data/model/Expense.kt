package com.droidcon.expenselogger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    val title: String,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("category")
    val category: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("date")
    val date: Long,

    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @SerializedName("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

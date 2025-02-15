package com.salihakbas.ecommercecompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val city: String,
    val district: String,
    val code: String
)

package com.salihakbas.ecommercecompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creditCard")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cardName: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val cardOwnerName: String
)

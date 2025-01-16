package com.salihakbas.ecommercecompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainEntityModel(
    @PrimaryKey
    val id: Int,
    val name: String,
)
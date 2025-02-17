package com.salihakbas.ecommercecompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.data.model.CreditCard
import com.salihakbas.ecommercecompose.data.model.FavoriteProduct

@Database(
    entities = [Address::class, CreditCard::class, FavoriteProduct::class],
    version = 4,
    exportSchema = false
)
abstract class MainRoomDB : RoomDatabase() {
    abstract fun mainDao(): MainDao
}
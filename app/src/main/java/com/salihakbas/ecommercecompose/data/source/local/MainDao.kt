package com.salihakbas.ecommercecompose.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salihakbas.ecommercecompose.data.model.Address
import com.salihakbas.ecommercecompose.data.model.CreditCard

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Query("SELECT * FROM address")
    suspend fun getAllAddress(): List<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditCard(creditCard: CreditCard)

    @Query("SELECT * FROM creditCard")
    suspend fun getAllCreditCards(): List<CreditCard>
}
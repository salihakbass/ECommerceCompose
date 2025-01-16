package com.salihakbas.ecommercecompose.data.repository

import com.salihakbas.ecommercecompose.data.source.local.MainDao
import com.salihakbas.ecommercecompose.data.source.remote.MainService
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
    private val mainDao: MainDao,
) : MainRepository
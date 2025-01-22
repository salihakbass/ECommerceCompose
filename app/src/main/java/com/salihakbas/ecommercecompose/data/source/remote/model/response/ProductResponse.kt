package com.salihakbas.ecommercecompose.data.source.remote.model.response

import com.salihakbas.ecommercecompose.domain.model.Product

data class ProductResponse(
    val products: List<Product>
) : BaseResponse()
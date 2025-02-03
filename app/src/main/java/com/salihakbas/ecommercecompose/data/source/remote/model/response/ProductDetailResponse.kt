package com.salihakbas.ecommercecompose.data.source.remote.model.response

import com.salihakbas.ecommercecompose.domain.model.ProductDetail

data class ProductDetailResponse(
    val product: ProductDetail?
) : BaseResponse()
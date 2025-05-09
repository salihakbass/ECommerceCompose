package com.salihakbas.ecommercecompose.data.source.remote.model.response

import com.salihakbas.ecommercecompose.domain.model.Category

data class CategoryResponse(
    val categories: List<Category>
) : BaseResponse()
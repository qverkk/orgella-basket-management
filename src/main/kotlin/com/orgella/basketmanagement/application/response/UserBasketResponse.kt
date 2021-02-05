package com.orgella.basketmanagement.application.response

import java.util.*

data class UserBasketResponse(
    val userId: UUID,
    val products: List<UserBasketItemResponse>
)

data class UserBasketItemResponse(
    val amount: Int,
    val product: ProductResponse
)

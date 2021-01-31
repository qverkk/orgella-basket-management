package com.orgella.basketmanagement.application.response

import java.util.*

data class UserBasketResponse(
    val userId: UUID,
    val products: List<ProductResponse>
)

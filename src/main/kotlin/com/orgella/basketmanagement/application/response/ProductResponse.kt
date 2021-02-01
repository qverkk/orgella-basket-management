package com.orgella.basketmanagement.application.response

import java.math.BigDecimal
import java.util.*

data class ProductResponse(
    var id: UUID,
    val title: String,
    var auctionPath: String,
    var quantity: Int,
    var price: BigDecimal,
    var thumbnail: String
)

package com.orgella.basketmanagement.application.request

data class AddBasketItemsRequest(
    val basketItems: List<BasketItemsRequest>
)

data class BasketItemsRequest(
    val auctionPath: String,
    val quantity: Int
)

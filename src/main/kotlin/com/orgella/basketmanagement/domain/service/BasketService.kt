package com.orgella.basketmanagement.domain.service

import com.orgella.basketmanagement.domain.BasketEntity
import com.orgella.basketmanagement.domain.BasketItem
import java.util.*

interface BasketService {

    fun findBasketForUserId(userId: UUID): Optional<BasketEntity>

    fun addItemToBasketForUserId(userId: UUID, basketItem: BasketItem): Boolean

    fun removeBasketItemForUserId(userId: UUID, basketItem: BasketItem)

    fun createBasketWithItemForUserId(userId: UUID, basketItem: BasketItem): Boolean

    fun createBasketWithItemsForUserId(userId: UUID, basketItems: List<BasketItem>): BasketEntity
}
package com.orgella.basketmanagement.domain.service

import com.orgella.basketmanagement.domain.BasketEntity
import com.orgella.basketmanagement.domain.BasketItem
import com.orgella.basketmanagement.domain.repository.BasketRepository
import java.util.*

class DomainBasketService(
    private val basketRepository: BasketRepository
): BasketService {
    override fun findBasketForUserId(userId: UUID): Optional<BasketEntity> {
        TODO("Not yet implemented")
    }

    override fun addItemToBasketForUserId(userId: UUID, basketItem: BasketItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeBasketItemForUserId(userId: UUID, basketItem: BasketItem) {
        TODO("Not yet implemented")
    }

    override fun createBasketWithItemForUserId(userId: UUID, basketItem: BasketItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun createBasketWithItemsForUserId(userId: UUID, basketItems: List<BasketItem>): Boolean {
        TODO("Not yet implemented")
    }

}
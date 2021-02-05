package com.orgella.basketmanagement.domain.service

import com.orgella.basketmanagement.domain.BasketEntity
import com.orgella.basketmanagement.domain.BasketItem
import com.orgella.basketmanagement.domain.repository.BasketRepository
import java.util.*

class DomainBasketService(
    private val basketRepository: BasketRepository
) : BasketService {
    override fun findBasketForUserId(userId: UUID): Optional<BasketEntity> {
        return basketRepository.findBasketForUserId(userId)
    }

    override fun addItemToBasketForUserId(userId: UUID, basketItem: BasketItem) {
        val basketEntity = basketRepository.findBasketForUserId(userId).orElse(
            BasketEntity(
                UUID.randomUUID(),
                userId.toString(),
                mutableListOf(),
                0
            )
        )

        val existingItem = basketEntity.productPaths.firstOrNull { it.auctionPath == basketItem.auctionPath }
        if (existingItem != null) {
            existingItem.quantity = basketItem.quantity
        } else {
            basketEntity.productPaths.add(basketItem)
        }
        basketRepository.save(basketEntity)
    }

    override fun removeBasketItemForUserId(userId: UUID, basketItemPath: String) {
        val basketEntity = basketRepository.findBasketForUserId(userId).orElse(
            BasketEntity(
                UUID.randomUUID(),
                userId.toString(),
                mutableListOf(),
                0
            )
        )

        val foundBasketItem = basketEntity.productPaths.first { it.auctionPath == basketItemPath }
        basketEntity.productPaths.remove(foundBasketItem)
        basketRepository.save(basketEntity)

    }

    override fun createBasketWithItemForUserId(userId: UUID, basketItem: BasketItem) {
        val basketEntity = BasketEntity(
            UUID.randomUUID(),
            userId.toString(),
            mutableListOf(basketItem),
            0
        )

        basketRepository.save(basketEntity)
    }

    override fun createBasketWithItemsForUserId(userId: UUID, basketItems: MutableList<BasketItem>): BasketEntity {
        val basketEntity = BasketEntity(
            UUID.randomUUID(),
            userId.toString(),
            basketItems,
            0
        )

        basketRepository.save(basketEntity)
        return basketEntity
    }

}
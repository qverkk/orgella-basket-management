package com.orgella.basketmanagement.domain.repository

import com.orgella.basketmanagement.domain.BasketEntity
import java.util.*

interface BasketRepository {

    fun findBasketForUserId(userId: UUID): Optional<BasketEntity>
}
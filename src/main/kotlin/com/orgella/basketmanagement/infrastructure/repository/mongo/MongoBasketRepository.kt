package com.orgella.auctionsmanagement.infrastructure.repository.mongo

import com.orgella.basketmanagement.domain.BasketEntity
import com.orgella.basketmanagement.domain.repository.BasketRepository
import com.orgella.basketmanagement.infrastructure.repository.mongo.SpringDataMongoBasketRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
class MongoBasketRepository(
    val basketRepository: SpringDataMongoBasketRepository
) : BasketRepository {
    override fun findBasketForUserId(userId: UUID): Optional<BasketEntity> {
        return basketRepository.findByUserId(userId)
    }
}
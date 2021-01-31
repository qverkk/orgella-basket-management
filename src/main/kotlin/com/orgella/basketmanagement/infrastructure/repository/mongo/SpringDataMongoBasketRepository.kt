package com.orgella.basketmanagement.infrastructure.repository.mongo

import com.orgella.basketmanagement.domain.BasketEntity
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface SpringDataMongoBasketRepository : MongoRepository<BasketEntity, UUID> {

    fun findByUserId(userId: UUID): Optional<BasketEntity>
}
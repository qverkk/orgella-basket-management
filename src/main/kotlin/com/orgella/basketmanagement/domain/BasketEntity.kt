package com.orgella.basketmanagement.domain

import org.springframework.data.annotation.Id
import java.util.*

data class BasketEntity(
    @field:Id
    val id: UUID,
    val userId: String,
    val productPaths: MutableList<BasketItem>
)

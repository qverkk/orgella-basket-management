package com.orgella.basketmanagement.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.Indexed
import java.util.*

data class BasketEntity(
    @field:Id
    val id: UUID,
    @field:Indexed(unique = true)
    val userId: String,
    val productPaths: MutableList<BasketItem>,
    @field:Version
    val version: Int
)

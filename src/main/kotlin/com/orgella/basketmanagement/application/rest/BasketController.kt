package com.orgella.basketmanagement.application.rest

import com.orgella.basketmanagement.application.response.UserBasketResponse
import com.orgella.basketmanagement.domain.service.BasketService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("basket")
class BasketController(
    private val basketService: BasketService
) {

    @GetMapping("/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("#userId == authentication.principal.userId OR hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    fun getBasketForUserId(@PathVariable userId: String): ResponseEntity<UserBasketResponse> {
        val uid = UUID.fromString(userId)
        val userBasket = basketService.findBasketForUserId(uid).orElseGet {
            basketService.createBasketWithItemsForUserId(uid, emptyList())
        }

        return ResponseEntity.ok(
            UserBasketResponse(
                uid,
                emptyList()
            )
        )
    }
}
package com.orgella.basketmanagement.application.rest

import com.orgella.basketmanagement.application.feign.AuctionsServiceClient
import com.orgella.basketmanagement.application.request.AddBasketItemsRequest
import com.orgella.basketmanagement.application.response.GetBasketProductsResponse
import com.orgella.basketmanagement.application.response.ProductResponse
import com.orgella.basketmanagement.application.response.UserBasketItemResponse
import com.orgella.basketmanagement.application.response.UserBasketResponse
import com.orgella.basketmanagement.domain.BasketEntity
import com.orgella.basketmanagement.domain.BasketItem
import com.orgella.basketmanagement.domain.service.BasketService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("basket")
class BasketController(
    private val basketService: BasketService,
    private val auctionsServiceClient: AuctionsServiceClient
) {

    @GetMapping("/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("#userId == authentication.principal.userId OR hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    fun getBasketForUserId(@PathVariable userId: String): ResponseEntity<UserBasketResponse> {
        val uid = UUID.fromString(userId)
        val userBasket = basketService.findBasketForUserId(uid).orElseGet {
//            basketService.createBasketWithItemsForUserId(uid, mutableListOf())
            BasketEntity(UUID.randomUUID(), userId, mutableListOf(), 0)
        }

        val basketItems = if (userBasket.productPaths.isNotEmpty())
            auctionsServiceClient.getAuctionsForBasketPaths(
                userBasket
                    .productPaths
                    .map { it.auctionPath }
            )
        else GetBasketProductsResponse(emptyList())

        return ResponseEntity.ok(
            UserBasketResponse(
                uid,
                basketItems.items.map {
                    UserBasketItemResponse(
                        userBasket.productPaths.stream().filter { userItem -> userItem.auctionPath == it.auctionPath }
                            .map { userItem -> userItem.quantity }.findFirst().orElse(0),
                        it
                    )
                }
            )
        )
    }

    @DeleteMapping("/{userId}/{productPath}")
    @PreAuthorize("#userId == authentication.principal.userId OR hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    fun deleteBasketItemForUserId(@PathVariable userId: String, @PathVariable productPath: String) {
        val uid = UUID.fromString(userId)
        basketService.removeBasketItemForUserId(uid, productPath);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId OR hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    fun addBasketItemsForUserId(
        @PathVariable userId: String,
        @RequestBody addBasketItems: AddBasketItemsRequest
    ): ResponseEntity<String> {
        val uid = UUID.fromString(userId)
        val userBasket = basketService.findBasketForUserId(uid).orElseGet {
            basketService.createBasketWithItemsForUserId(uid, mutableListOf())
        }

        val basketItems = if (addBasketItems.basketItems.isNotEmpty())
            auctionsServiceClient.getAuctionsForBasketPaths(
                addBasketItems
                    .basketItems
                    .map { it.auctionPath }
            )
        else GetBasketProductsResponse(emptyList())

        addBasketItems.basketItems.forEach {
            val item: ProductResponse? = basketItems.items.firstOrNull { item -> item.auctionPath == it.auctionPath }
            if (item != null) {
                val userBasketItem =
                    userBasket.productPaths.firstOrNull { userBasketItem -> userBasketItem.auctionPath == it.auctionPath }

                var quantity = it.quantity
                if (quantity > item.quantity || (userBasketItem != null && quantity + userBasketItem.quantity > item.quantity)) {
                    quantity = item.quantity
                } else {
                    quantity += userBasketItem?.quantity ?: 0
                }
                basketService.addItemToBasketForUserId(uid, BasketItem(it.auctionPath, quantity))
            }
        }

        return ResponseEntity.ok("Added")
    }

}
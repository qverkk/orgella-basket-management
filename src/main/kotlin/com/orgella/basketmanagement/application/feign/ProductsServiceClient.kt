package com.orgella.basketmanagement.application.feign

import com.orgella.basketmanagement.application.response.GetBasketProductsResponse
import feign.FeignException
import feign.hystrix.FallbackFactory
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "auctions-ws", fallbackFactory = AuctionsFallbackFactory::class)
interface AuctionsServiceClient {

    @GetMapping("/auctions/details/basket")
    fun getAuctionsForBasketPaths(@RequestParam auctionPaths: List<String>): GetBasketProductsResponse
}

@Component
internal class AuctionsFallbackFactory : FallbackFactory<AuctionsServiceClient> {
    override fun create(cause: Throwable): AuctionsServiceClient {
        return AuctionsServiceFallback(cause)
    }
}

internal class AuctionsServiceFallback(
    private val cause: Throwable? = null
) : AuctionsServiceClient {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun getAuctionsForBasketPaths(@RequestParam auctionPaths: List<String>): GetBasketProductsResponse {
        if (cause is FeignException
            && cause.status() == 404
        ) {
            logger.error(
                "404 error took place when getAuctionsForBasketPaths was called with paths: "
                        + auctionPaths + ". Error message: "
                        + cause.getLocalizedMessage()
            )
        } else {
            logger.error(
                "Other error took place: " + cause!!.localizedMessage
            )
        }
        return GetBasketProductsResponse(emptyList())
    }
}
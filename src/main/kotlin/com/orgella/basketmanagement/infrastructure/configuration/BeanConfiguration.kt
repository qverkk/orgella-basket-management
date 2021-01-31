package com.orgella.basketmanagement.infrastructure.configuration

import com.orgella.basketmanagement.domain.repository.BasketRepository
import com.orgella.basketmanagement.domain.service.BasketService
import com.orgella.basketmanagement.domain.service.DomainBasketService
import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class BeanConfiguration {

    @Bean
    fun auctionsService(basketRepository: BasketRepository): BasketService {
        return DomainBasketService(basketRepository)
    }

    @Bean
    @Profile("production")
    fun feignProductionLoggingLevel(): Logger.Level = Logger.Level.NONE

    @Bean
    @Profile("!production")
    fun feignDefaultFeignLoggingLevel(): Logger.Level = Logger.Level.FULL
}
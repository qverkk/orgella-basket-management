package com.orgella.basketmanagement.infrastructure.configuration.security

import org.springframework.security.core.GrantedAuthority

data class UserInfo(
    val username: String,
    val userId: String,
    val roles: List<GrantedAuthority>
)

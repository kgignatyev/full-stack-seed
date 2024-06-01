package com.kgignatyev.fss.service.acceptance.data


data class User(
    val id: String,
    val name: String,
    val password: String,
    val email: String,
    val roles: List<String> = emptyList()
)

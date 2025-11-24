package com.example.demo

import java.time.Instant


data class User(
    val id: Long,
    val username: String,
    val email: String,
    val isCorporate: Boolean
)



data class Account(
    val id: Long,
    val userId: Long,
    var balance: Double = 0.0
)





data class Transaction(
    val id: Long,
    val fromAccountId: Long?,
    val toAccountId: Long?,
    val amount: Double,
    val type: String,
    val timestamp: Instant = Instant.now()
)

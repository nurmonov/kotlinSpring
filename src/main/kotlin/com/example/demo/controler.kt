package com.example.demo

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestParam username: String,
               @RequestParam email: String,
               @RequestParam isCorporate: Boolean) =
        userService.create(username, email, isCorporate)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = userService.getById(id)

    @GetMapping
    fun getAll() = userService.getAll()
}



@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping
    fun createAccount(@RequestParam userId: Long) =
        accountService.createAccount(userId)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        accountService.getById(id)
}



@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val txService: TransactionService
) {

    @PostMapping("/deposit")
    fun deposit(@RequestParam id: Long, @RequestParam amount: Double) =
        txService.deposit(id, amount)

    @PostMapping("/withdraw")
    fun withdraw(@RequestParam id: Long, @RequestParam amount: Double) =
        txService.withdraw(id, amount)

    @PostMapping("/transfer")
    fun transfer(
        @RequestParam fromId: Long,
        @RequestParam toId: Long,
        @RequestParam amount: Double
    ) = txService.transfer(fromId, toId, amount)
}

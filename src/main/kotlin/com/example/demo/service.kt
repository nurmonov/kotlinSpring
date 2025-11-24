package com.example.demo


import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepository
) {
    fun create(username: String, email: String, isCorporate: Boolean) =
        userRepo.create(username, email, isCorporate)

    fun getById(id: Long) = userRepo.findById(id)
    fun getAll() = userRepo.findAll()
}


@Service
class AccountService(
    private val accountRepo: AccountRepository,
    private val userRepo: UserRepository
) {

    fun createAccount(userId: Long): Any {
        val user = userRepo.findById(userId) ?: return "sen topilmading borib uzinigni ruyhatdan utqiz"

        val userAccounts = accountRepo.findByUserId(userId)

        if (user.isCorporate && userAccounts.size >= 10)
            return "limitga yetgan qaysidir kartani uchir"

        if (!user.isCorporate && userAccounts.size >= 5)
            return "limitdan oshgan bank qora ruhhatidan chiqsang 10 tagacha olasan "

        return accountRepo.create(userId)
    }

    fun getById(id: Long) = accountRepo.findById(id)
}


@Service
class TransactionService(
    private val accountRepo: AccountRepository,
    private val txRepo: TransactionRepository
) {

    fun deposit(accountId: Long, amount: Double): Any {
        val acc = accountRepo.findById(accountId) ?: return "sening akkaunting yuqqku "
        acc.balance += amount

        val tx = Transaction(
            id = txRepo.nextId(),
            fromAccountId = null,
            toAccountId = acc.id,
            amount = amount,
            type = "DEPOSIT"
        )

        txRepo.create(tx)
        return acc
    }

    fun withdraw(accountId: Long, amount: Double): Any {
        val acc = accountRepo.findById(accountId) ?: return "sening akkaunting yuqku "
        if (acc.balance < amount) return "pul yetmidi"

        acc.balance -= amount

        val tx = Transaction(
            id = txRepo.nextId(),
            fromAccountId = acc.id,
            toAccountId = null,
            amount = amount,
            type = "WITHDRAW"
        )

        txRepo.create(tx)
        return acc
    }

    fun transfer(fromId: Long, toId: Long, amount: Double): Any {
        val from = accountRepo.findById(fromId) ?: return "senning kartang yuqku dddd🤣"
        val to = accountRepo.findById(toId) ?: return "qabul qiluvchi ulgan🤣"

        val commission = if (from.userId != to.userId) amount * 0.5 else 0.0

        val total = amount + commission

        if (from.balance < total)
            return "pul yuq bor kartanga pul son  gaday "

        from.balance -= total
        to.balance += amount

        val tx = Transaction(
            id = txRepo.nextId(),
            fromAccountId = fromId,
            toAccountId = toId,
            amount = amount,
            type = "TRANSFER"
        )

        txRepo.create(tx)
        return "Success"
    }
}

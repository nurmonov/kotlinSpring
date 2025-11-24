package com.example.demo


import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    private val users = ConcurrentHashMap<Long, User>()
    private val idGenerator = AtomicLong(1)

    fun save(user: User): User {
        users[user.id] = user
        return user
    }
    fun generateId(): Long = idGenerator.incrementAndGet()

    fun create(username: String, email: String, isCorporate: Boolean): User {
        val id = idGenerator.getAndIncrement()
        val user = User(id, username, email, isCorporate)
        users[id] = user
        return user
    }

    fun findById(id: Long) = users[id]
    fun findAll() = users.values.toList()
}




@Repository
class AccountRepository {
    private val accounts = ConcurrentHashMap<Long, Account>()
    private val idGenerator = AtomicLong(1)

    fun create(userId: Long): Account {
        val id = idGenerator.getAndIncrement()
        val acc = Account(id, userId)
        accounts[id] = acc
        return acc
    }
    fun save(userId: Long, balance: Double = 0.0): Account {
        val id = idGenerator.getAndIncrement()
        val account = Account(id.toLong(), userId.toLong(), balance)
        accounts[id] = account
        return account
    }


    fun findById(id: Long) = accounts[id]
    fun findByUserId(userId: Long) = accounts.values.filter { it.userId == userId }
}



@Repository
class TransactionRepository {

    private val tx = ConcurrentHashMap<Long, Transaction>()
    private val idGen = AtomicLong(1)

    fun create(transaction: Transaction): Transaction {
        tx[transaction.id] = transaction
        return transaction
    }

    fun nextId(): Long = idGen.getAndIncrement()
}

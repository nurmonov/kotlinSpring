package com.example.demo


import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class StartupDataLoader(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) {

    @PostConstruct
    fun init() {



        val user1 = userRepository.save(
            User(
                id = userRepository.generateId(),
                username = "ketmon",
                email = "java@example.com",
                isCorporate = false
            )
        )

        val user2 = userRepository.save(
            User(
                id = userRepository.generateId(),
                username = "qodirali",
                email = "kotlin@example.com",
                isCorporate = true
            )
        )

        val account1 = accountRepository.save(user1.id.toLong(), 1500.0)
        val account2 = accountRepository.save(user2.id.toLong(), 5000.0)

        println("akkaunt yaratildi: $account1, $account2")


        println("70 75 iyuuuuuuu🤣🤣")
    }
}

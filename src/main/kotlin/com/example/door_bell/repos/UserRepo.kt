package com.example.door_bell.repos

import com.example.door_bell.domain.User
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicInteger

@Repository
class UserRepo {

    private val idCounter = AtomicInteger(1)
    private val users = mutableListOf<User>()

    fun save(user: User): User {
        user.id = idCounter.getAndAdd(1).toLong()
        users.add(user)
        return user
    }

    fun findUserByUserId(id: Long): User? = users.find { it.id == id }
    fun findUserByEspId(id: String): User? = users.find { it.espId == id }
}

package com.kgignatyev.fss.service.acceptance.security

import com.kgignatyev.fss.service.acceptance.TestsContext
import com.kgignatyev.fss.service.acceptance.data.CfgValues
import com.kgignatyev.fss.service.acceptance.data.User
import io.cucumber.java.en.Given
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class SecurityHelper {

    @Resource
    lateinit var conf: CfgValues


    fun getUser(user: String?): User {
        user ?: throw Exception("User name is not provided")
        //by default Auth0 uses email as username
        return  User(user,conf.users["$user.name"]!!,
            conf.users["$user.password"]!!, conf.users["$user.name"]!!)
    }

    fun <T> runAsUser(userName: String, block: () -> T): T {
        return runAsUser(getUser(userName), block)
    }

    fun <T> runAsUser(user: User, block: () -> T): T {
        val oldUser = TestsContext.currentUser
        TestsContext.currentUser = user
        try {
            return block()
        } finally {
            TestsContext.currentUser = oldUser
        }
    }
}

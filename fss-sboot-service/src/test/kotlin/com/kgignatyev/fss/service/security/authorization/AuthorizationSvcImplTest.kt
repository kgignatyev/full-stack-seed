package com.kgignatyev.fss.service.security.authorization

import com.kgignatyev.fss.service.accounts.Account
import com.kgignatyev.fss.service.security.SecurityPolicy
import com.kgignatyev.fss.service.security.User
import com.kgignatyev.fss.service.security.storage.SecurityPoliciesRepo
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.cache.CacheManager
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class AuthorizationSvcImplTest {


    @Test
    fun testGetSecurityPoliciesForUser() {
        val cacheManagerMock = mock(CacheManager::class.java)
        val securityPoliciesRepoMock = mock(SecurityPoliciesRepo::class.java)
        val u1 = User().apply { id = "u1" }
        Mockito.`when`(securityPoliciesRepoMock.findByUserId(u1.id))
            .thenReturn(
                listOf(
                    makePolicy(u1.id, "Account/1, *"),
                    makePolicy(u1.id, "Account/2, *"),
                )
            )
        val u2 = User().apply { id = "u2" }
        Mockito.`when`(securityPoliciesRepoMock.findByUserId(u2.id))
            .thenReturn(
                listOf(
                    makePolicy(u2.id, "Account/3, *"),
                    makePolicy(u2.id, "Account/4, *"),
                )
            )

        val admin = User().apply { id = "admin" }
        Mockito.`when`(securityPoliciesRepoMock.findByUserId(admin.id))
            .thenReturn(
                listOf(
                    makePolicy(admin.id, "*, *"),
                )
            )

        val svc = AuthorizationSvcImpl(securityPoliciesRepoMock,cacheManagerMock )
        svc.enableCasbinLogging = true
        val u1Enforcer = svc.createEnforcerForUser(u1.id)
        val u2Enforcer = svc.createEnforcerForUser(u2.id)
        val adminEnforcer = svc.createEnforcerForUser(admin.id)

        assertTrue { u1Enforcer.enforce(u1, Account().apply { id = "1" }, "read") }
        assertTrue { u1Enforcer.enforce(u1, Account().apply { id = "2" }, "write") }
        assertFalse { u1Enforcer.enforce(u1, Account().apply { id = "3" }, "write") }
        assertTrue { u2Enforcer.enforce(u2, Account().apply { id = "3" }, "read") }
        assertTrue { u2Enforcer.enforce(u2, Account().apply { id = "4" }, "write") }
        assertTrue { adminEnforcer.enforce(admin, Account().apply { id = "1" }, "write") }
        assertTrue { adminEnforcer.enforce(admin, Account().apply { id = "4" }, "write") }
    }

    private fun makePolicy(userId: String, s: String): SecurityPolicy {
        return SecurityPolicy().apply {
            this.userId = userId
            this.policy = s
        }
    }
}

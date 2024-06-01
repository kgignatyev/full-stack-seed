package com.kgignatyev.fss.service.security.svc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.kgignatyev.fss.service.accounts.AccountEvent
import com.kgignatyev.fss.service.common.events.CrudEventType
import com.kgignatyev.fss.service.security.*
import com.kgignatyev.fss.service.security.storage.SecurityPoliciesRepo
import jakarta.annotation.Resource
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class SecuritySvcImpl(
    val userSvc: UserSvc,
    val policiesRepo: SecurityPoliciesRepo
) : SecuritySvc {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${okta.oauth2.issuer}")
    lateinit var issuer: String

    @Resource
    lateinit var om:ObjectMapper

    @ApplicationModuleListener
    @Transactional
    fun onAccountEvent(event: AccountEvent) {
        logger.info("Account event: ${event.data.id} ${event.eventType}")
        if( event.eventType == CrudEventType.CREATED){
            val ownerId = event.data.ownerId
            val policy = SecurityPolicy()
            policy.userId = ownerId
            policy.name = "account-owner"
            policy.policy = "Account/${event.data.id}, *"
            policiesRepo.save(policy)
        }
    }

    override fun getSecurityPoliciesForUser(userId: String): List<SecurityPolicy> {
        return policiesRepo.findByUserId(userId)
    }

    override fun ensureCurrentUserIsStored() {
        val callerInfo = getCallerInfo()
        val user = callerInfo.currentUser
        if (user == CallerInfo.anonymousUser) {
            throw IllegalStateException("Anonymous user is not allowed to perform this operation")
        }
        val userO = userSvc.findByJwtSub(user.jwtSub)
        if (userO.isEmpty) {
            val savedUser = userSvc.save(user)
            callerInfo.currentUser = savedUser
        }

    }

    fun getUserInfoFromAuth0():User{
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.set("authorization", SecurityContext.httpHeaders.get()["authorization"]!!)
        val r = HttpEntity("", headers);
        val res = restTemplate.exchange("$issuer/userinfo", HttpMethod.GET,r,String::class.java)
        val info = om.readTree(  res.body ) as ObjectNode
        logger.debug("Received UserInfo: $info")
        val u = User()
        u.jwtSub = info.get("sub").asText()
        u.name = info.get("name").asText()
        u.email = info.get("email").asText()
        return u
    }

    override fun getCallerInfo(): CallerInfo {
        val auth = SecurityContextHolder.getContext().authentication
        logger.debug("principal:${auth.principal} \n\t details:${auth.details}")
        val principal = auth.principal
        val currentCallerInfo = SecurityContext.callerInfo.get()
        return if (currentCallerInfo != null) {
            return currentCallerInfo
        } else {
            val ci = when (principal) {
                is Jwt -> {
                    val realUserSub = principal.claims["sub"] as String
                    val userO = userSvc.findByJwtSub(realUserSub)
                    val realUser = if (userO.isEmpty) {
                        getUserInfoFromAuth0()
                    } else {
                        userO.get()
                    }
                    val callerInfo = CallerInfo()
                    callerInfo.currentUser = realUser
                    callerInfo.realUser = realUser
                    callerInfo
                }

                else -> {
                    CallerInfo.anonymousCaller
                }
            }
            SecurityContext.callerInfo.set(ci)
            ci
        }
    }

    @Transactional
    override fun deleteUser(userId: String) {
        policiesRepo.deleteByUserId(userId)
        userSvc.deleteById(userId)
    }
}

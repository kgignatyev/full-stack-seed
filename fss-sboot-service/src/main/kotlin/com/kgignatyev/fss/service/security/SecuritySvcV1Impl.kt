package com.kgignatyev.fss.service.security

import com.kgignatyev.fss.service.common.api.V1StatusHelpers.OK
import com.kgignatyev.fss.service.common.api.V1StatusHelpers.OK_ENTITY
import com.kgignatyev.fss_svc.api.fsssvc.SecurityServiceV1Api
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.*
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api"])
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"], methods = [RequestMethod.PATCH, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.PUT])
@RestController
class SecuritySvcV1Impl(val securitySvc: SecuritySvc,
                        val userSvc: UserSvc,
                        val conversionService: ConversionService): SecurityServiceV1Api{

    override fun searchUsers(v1SearchRequest: V1SearchRequest): ResponseEntity<V1UsersListResult> {
        val r = userSvc.search(v1SearchRequest.searchExpression, v1SearchRequest.sortExpression, v1SearchRequest.pagination.offset, v1SearchRequest.pagination.limit)
        val res = V1UsersListResult()
        res.items = r.items.map { conversionService.convert(it,V1User::class.java) }
        res.listSummary = r.summary.toApiListSummary()
        return ResponseEntity.ok(res)
    }

    override fun getSecurityPoliciesForUser(userId: String): ResponseEntity<List<V1SecurityPolicy>> {
        val effectiveUserId = if( userId == "my") securitySvc.getCallerInfo().currentUser.id else userId
        val policies:List<SecurityPolicy> = securitySvc.getSecurityPoliciesForUser(effectiveUserId)
        return ResponseEntity.ok(policies.map { conversionService.convert(it,V1SecurityPolicy::class.java)!! })
    }

    override fun deleteUserById(userId: String): ResponseEntity<V1Status> {
        securitySvc.deleteUser(userId)
        return OK_ENTITY
    }
}
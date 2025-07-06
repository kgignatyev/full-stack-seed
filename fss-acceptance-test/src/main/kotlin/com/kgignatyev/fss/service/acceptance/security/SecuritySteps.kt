package com.kgignatyev.fss.service.acceptance.security

import com.kgignatyev.fss.service.acceptance.TestsContext
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.SecurityServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.infrastructure.ClientException
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Pagination
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1SearchRequest
import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeGreaterThan
import jakarta.annotation.Resource


class SecuritySteps {

    @Resource
    lateinit var securityHelper: SecurityHelper

    @Resource
    lateinit var securityApi: SecurityServiceV1Api

    @Then("user can retrieve own policies")
    fun user_can_retrieve_own_policies() {
        val u = securityApi.getUserById("me")
        u.email!! shouldBeEqual TestsContext.currentUser.email
        val policies = securityApi.getSecurityPoliciesForUser( "me" )
        policies.size shouldBeGreaterThan 0
    }

    @Then("user can't retrieve policies of other users")
    fun user_cant_retrieve_policies_of_other_users() {

        val users = securityHelper.runAsUser("admin"){
            val r = V1SearchRequest("email like '%kgignaty%'", "email asc",
                V1Pagination(1, 10))
            securityApi.searchUsers(r).items
        }

        val otherUser = users.first { it.email != TestsContext.currentUser.email }
        try {
            val policies = securityApi.getSecurityPoliciesForUser(otherUser.id)
            throw IllegalStateException("Should not be able to retrieve policies of other users")
        }catch (e: Exception){
            when(e){
                is ClientException -> e.statusCode shouldBeEqual 403
                else -> throw e
            }
        }
    }
}

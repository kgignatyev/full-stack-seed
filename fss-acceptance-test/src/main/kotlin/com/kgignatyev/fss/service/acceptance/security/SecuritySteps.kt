package com.kgignatyev.fss.service.acceptance.security

import com.kgignatyev.fss.service.acceptance.TestsContext
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.SecurityServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Pagination
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1SearchRequest
import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeGreaterThan
import jakarta.annotation.Resource
import org.junit.Assert
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class SecuritySteps {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Resource
    lateinit var securityHelper: SecurityHelper

    @Resource
    lateinit var securityApi: SecurityServiceV1Api

    @Then("user can retrieve own policies")
    fun user_can_retrieve_own_policies() {
        val u = securityApi.getUserById("me")
        u.email!! shouldBeEqual TestsContext.currentUser.email
        val policies = securityApi.getSecurityPoliciesForUser("me")
        policies.size shouldBeGreaterThan 0
    }

    @Then("user can't retrieve policies of other users")
    fun user_cant_retrieve_policies_of_other_users() {

        val users = securityHelper.runAsUser("admin") {
            val r = V1SearchRequest(
                "email like '%kgignaty%'", "email asc",
                V1Pagination(1, 10)
            )
            securityApi.searchUsers(r).items
        }

        val otherUser = users.first { it.email != TestsContext.currentUser.email }

        val policies = securityApi.getSecurityPoliciesForUser(otherUser.id)

        val userIdsInPolicies = policies.map { it.userId }.toSet()
        Assert.assertEquals(1, userIdsInPolicies.size)
        val policiesForUserId = userIdsInPolicies.first()
        logger.info("User ${TestsContext.currentUser.id} found ${policies.size} policies for user $policiesForUserId")
        if( "guest" != policiesForUserId ) {
            throw IllegalStateException("Should not be able to retrieve policies of other users")
        }

    }
}

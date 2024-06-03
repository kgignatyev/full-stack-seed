package com.kgignatyev.fss.service.acceptance.sys_management

import com.kgignatyev.fss.service.acceptance.ApiHelpers.createSearchRequest
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.AccountsServiceV1Api
import io.cucumber.java.PendingException
import io.cucumber.java.en.Then
import io.kotest.matchers.ints.shouldBeGreaterThan
import jakarta.annotation.Resource


class SystemManagementSteps {

    @Resource
    lateinit var accountsApi: AccountsServiceV1Api

    @Then("current user can search accounts")
    fun admin_user_can_search_accounts() {
        val r = createSearchRequest( "name like '%t%'",
            "name asc")
        val res = accountsApi.searchAccounts( r )
        val accountOwners= res.items.map { it.ownerId }.toSet()
        accountOwners.size shouldBeGreaterThan 1
    }

    @Then("current user can impersonate normal user {string}")
    fun admin_user_can_impersonate_normal_user(userName: String) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("current user has access application metrics {string}")
    fun metrics_user_has_access_to_application_metrics(yesNo: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }
}

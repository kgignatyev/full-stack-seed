package com.kgignatyev.fss.service.acceptance

import com.kgignatyev.fss.service.acceptance.ApiHelpers.createSearchRequest
import com.kgignatyev.fss.service.acceptance.account.AccountHelpers
import com.kgignatyev.fss.service.acceptance.security.SecurityHelper
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.AccountsServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.SecurityServiceV1Api
import io.cucumber.java.en.Then
import io.kotest.matchers.ints.shouldBeExactly
import jakarta.annotation.Resource


class InitSteps {

    @Resource
    lateinit var accountHelpers: AccountHelpers

    @Resource
    lateinit var accountsApi: AccountsServiceV1Api

    @Resource
    lateinit var securityApi: SecurityServiceV1Api

    @Resource
    lateinit var securityHelper: SecurityHelper

    @Then("we delete test account {string}")
    fun we_delete_account( accountName: String?) {
        val accounts = accountHelpers.findAccountsByName(accountName!!).items
        if(accounts.isEmpty()) {
            return
        }else {
            accounts.size shouldBeExactly 1
            val account = accounts[0]
            accountsApi.deleteAccountById(account.id)
        }
    }

    @Then("we delete test user {string}")
    fun we_delete_user( user: String) {
        val u = securityHelper.getUser(user)
        val foundUsers = securityApi.searchUsers(
            createSearchRequest("name = '${u.name}'", "name asc")).items
        if(foundUsers.isEmpty()) {
            return
        }else {
            foundUsers.size shouldBeExactly 1
            securityApi.deleteUserById(foundUsers[0].id)
        }
    }

}

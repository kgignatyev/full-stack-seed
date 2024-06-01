package com.kgignatyev.fss.service.acceptance.sys_management

import io.cucumber.java.PendingException
import io.cucumber.java.en.Then


class SystemManagementSteps {
    @Then("current user can search accounts")
    fun admin_user_can_search_accounts() {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("current user can impersonate normal user {string}")
    fun admin_user_can_impersonate_normal_user(userName: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("current user has access application metrics {string}")
    fun metrics_user_has_access_to_application_metrics(yesNo: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }
}

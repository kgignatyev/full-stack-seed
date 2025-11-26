package com.kgignatyev.fss.service.acceptance.sys_management

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.kgignatyev.fss.service.acceptance.ApiHelpers.createSearchRequest
import com.kgignatyev.fss.service.acceptance.data.CfgValues
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.AccountsServiceV1Api
import io.cucumber.java.PendingException
import io.cucumber.java.en.Then
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import jakarta.annotation.Resource
import org.apache.commons.io.IOUtils
import java.net.URI
import java.net.URL
import java.nio.charset.Charset


class SystemManagementSteps {

    @Resource
    lateinit var accountsApi: AccountsServiceV1Api

    @Resource
    lateinit var cfg: CfgValues

    @Resource
    lateinit var om: ObjectMapper

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

    @Then( "health checkpoint is available and is UP")
    fun health_checkpoint_is_available_and_up(){
        val healthUrl = cfg.fssApiBaseUrl.replace("/api","/actuator/health")
        val healthData = om.readValue(URI.create(healthUrl).toURL().openStream(), JsonNode::class.java)
        println(healthData)
        healthData.get("status").asText() shouldBe "UP"
    }

    @Then( "prometheus metrics are available")
    fun prometheus_metrics_are_available(){
        val prometheusMetrics = cfg.fssApiBaseUrl.replace("/api","/actuator/prometheus")
        val metricLines = URI.create(prometheusMetrics).toURL().openStream().use { stream ->
             IOUtils.readLines(stream, Charset.defaultCharset())
        }
        println(metricLines)
        metricLines.size shouldBeGreaterThan 10

    }
}

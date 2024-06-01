package com.kgignatyev.fss.service.acceptance


import com.kgignatyev.fss.service.acceptance.data.CfgValues
import com.kgignatyev.fss.service.acceptance.security.SecurityHelper
import io.cucumber.java.en.Given
import io.kotest.matchers.ints.shouldBeGreaterThan
import jakarta.annotation.Resource


class CommonSteps {

    @Resource
    lateinit var authorizationInterceptor: AuthorizationInterceptor

    @Resource
    lateinit var securityHelper: SecurityHelper

    @Given("user {string}")
    fun given_user(userName: String?) {
          TestsContext.currentUser = securityHelper.getUser(userName)
    }

    @Given("user {string} is logged in")
    fun user_is_logged_in(userName: String?) {
        given_user(userName)
        val userToken = authorizationInterceptor.getToken()
        userToken.length shouldBeGreaterThan 10
    }


}

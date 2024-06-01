package com.kgignatyev.fss.service.security.authorization

import com.kgignatyev.fss.service.security.storage.SecurityPoliciesRepo
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets
import org.casbin.jcasbin.main.Enforcer
import org.casbin.jcasbin.model.Model
import org.casbin.jcasbin.persist.Helper
import org.casbin.jcasbin.util.Util
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthorizationSvc( val policiesRepo: SecurityPoliciesRepo) {

    lateinit var policyEvaluatorExpression: String

    @Value("\${casbin.enable-log}")
    lateinit var enableCasbinLogging: String

    val canDoAction = CanDoActionFunction()
    val isActiveFunction = IsActiveFunction()


    fun init() {
        val authorizationModelText = resourceAsText("fss_authorization_model.conf")
        val authzModelLines = authorizationModelText.split("\n")
        val prefix = "m ="
        val evalLine = authzModelLines.find { it.startsWith(prefix) }!!
        policyEvaluatorExpression = evalLine.substring(prefix.length).trim()
    }

    fun createEnforcerForUser( userId: String): Enforcer {
        val model = Model()
        val authorizationModelText = resourceAsText("fss_authorization_model.conf")
        model.loadModelFromText(authorizationModelText)
        val policies = policiesRepo.findByUserId(userId)
        policies.forEach { policy ->
            val pLine = "p, ${policy.userId}, ${policy.policy}"
            Helper.loadPolicyLine(pLine, model)
        }
        val enforcer = Enforcer(model)
        enforcer.addFunction(canDoAction.name, canDoAction)
        enforcer.addFunction(isActiveFunction.name, isActiveFunction)
        Util.enableLog = enableCasbinLogging.toBoolean()
        return enforcer
    }

    private fun resourceAsText(resource: String): String {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(resource)
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    }



}

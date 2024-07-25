package com.kgignatyev.fss.service.security

import org.casbin.jcasbin.main.Enforcer


interface AuthorizationSvc {

    fun getEvaluatorExpression(): String
    fun getEnforcerForUser( userId: String): Enforcer
    fun evictEnforcers()
    fun postConfigureEnforcer(enforcer: Enforcer)
}

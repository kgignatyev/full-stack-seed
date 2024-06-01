package com.kgignatyev.fss.service.security



interface SecuritySvc {
    fun getSecurityPoliciesForUser(userId: String): List<SecurityPolicy>
    fun ensureCurrentUserIsStored()
    fun getCallerInfo(): CallerInfo
    fun deleteUser(userId: String)
}

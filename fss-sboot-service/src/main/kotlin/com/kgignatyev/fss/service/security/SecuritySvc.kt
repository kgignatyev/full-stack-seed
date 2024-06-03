package com.kgignatyev.fss.service.security

import com.kgignatyev.fss.service.UnauthorizedException
import com.kgignatyev.fss.service.accounts.AccountEvent
import com.kgignatyev.fss.service.common.events.CrudEventType
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1User
import org.springframework.http.ResponseEntity
import java.util.*
import kotlin.jvm.Throws


interface SecuritySvc {
    fun getSecurityPoliciesForUser(userId: String): List<SecurityPolicy>
    fun ensureCurrentUserIsStored()
    fun getCallerInfo(): CallerInfo
    fun deleteUser(userId: String)
    fun onCrudEvent(o:Securable,event: CrudEventType)
    fun isCurrentUserAuthorized( o: Securable, action: String): Boolean
    @Throws(UnauthorizedException::class)
    fun checkCurrentUserAuthorized( o: Securable, action: String)
    fun isUserAuthorized( userId:String,o: Securable, action: String): Boolean
    fun getUserById(userId: String): Optional<User>
    fun updateUserById(u: User): User
}

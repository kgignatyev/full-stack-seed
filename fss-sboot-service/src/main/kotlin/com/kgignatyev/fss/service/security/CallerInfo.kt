package com.kgignatyev.fss.service.security


class CallerInfo {
    var currentUser:User = anonymousUser
    var realUser:User = anonymousUser // when impersonation is used

    companion object {

        const val X_IMPERSONATE = "X-Impersonate"

        val anonymousUser = User().apply {
            id = ""
            name = "anonymous"
            email = "anonymous@some.com"
        }

        val anonymousCaller = CallerInfo()
    }

    override fun toString(): String {
        return "CallerInfo(currentUser=$currentUser, realUser=$realUser)"
    }
}




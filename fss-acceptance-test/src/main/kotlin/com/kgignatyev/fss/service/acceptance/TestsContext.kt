package com.kgignatyev.fss.service.acceptance

import com.kgignatyev.fss.service.acceptance.data.User
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Job


object TestsContext {

    var currentJob: V1Job? = null

    val anonymousUser = User("anonymous", "anonymous", "anonymous", "anonymous@some.com")

    var currentUser: User = anonymousUser

}

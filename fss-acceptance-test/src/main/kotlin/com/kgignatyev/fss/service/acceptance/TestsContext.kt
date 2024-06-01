package com.kgignatyev.fss.service.acceptance

import com.kgignatyev.fss.service.acceptance.data.User


object TestsContext {

    val anonymousUser = User("anonymous", "anonymous", "anonymous", "anonymous@some.com")

    var currentUser: User = anonymousUser

}

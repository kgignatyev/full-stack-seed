package com.kgignatyev.fss.service.acceptance.data

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "fss")
class FssUsers {
    lateinit var users: HashMap<String, String>
}

@Component
class CfgValues( val fssUsers: FssUsers){
    var users: java.util.HashMap<String, String> = this.fssUsers.users

    @Value("\${auth0.issuer}")
    lateinit var auth0issuer:String

    @Value("\${fss.api.baseUrl}")
    lateinit var fssApiBaseUrl: String

    @Value("\${auth0.clientId}")
    lateinit var fssApiClientId: String

    @Value("\${auth0.clientSecret}")
    lateinit var fssApiClientSecret: String

    @Value("\${auth0.audience}")
    lateinit var fssApiAudience: String


}

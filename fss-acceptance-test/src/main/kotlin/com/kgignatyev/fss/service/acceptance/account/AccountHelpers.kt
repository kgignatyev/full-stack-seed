package com.kgignatyev.fss.service.acceptance.account

import com.kgignatyev.fss.service.acceptance.ApiHelpers.createSearchRequest
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.AccountsServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Account
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1AccountListResult
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class AccountHelpers {
    @Resource
    lateinit var accountsApi: AccountsServiceV1Api

    fun findAccountsByName(accountName: String): V1AccountListResult {
        val r = createSearchRequest( "name = '$accountName'",
            "name asc")
        return accountsApi.searchAccounts( r )
    }


}

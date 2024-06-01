package com.kgignatyev.fss.service.accounts.storage

import com.kgignatyev.fss.service.accounts.Account
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Repository
@Transactional
interface AccountsRepo : CrudRepository<Account, String>, JpaSpecificationExecutor<Account> {
    fun findByOwnerId(id: String): List<Account>
}



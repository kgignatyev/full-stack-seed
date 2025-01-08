package com.kgignatyev.fss.service.accounts

import com.kgignatyev.fss.service.common.data.SearchableRepo
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Service

@NoRepositoryBean

interface AccountsSvc: SearchableRepo<Account>,CrudRepository<Account, String>{

}

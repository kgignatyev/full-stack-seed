package com.kgignatyev.fss.service.security

import com.kgignatyev.fss.service.common.data.SearchableRepo
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.Optional

@NoRepositoryBean
interface UserSvc: SearchableRepo<User>, CrudRepository<User, String> {
    fun findByJwtSub(realUserSub: String): Optional<User>
}

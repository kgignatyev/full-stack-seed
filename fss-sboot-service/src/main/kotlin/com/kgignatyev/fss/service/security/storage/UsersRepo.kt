package com.kgignatyev.fss.service.security.storage

import com.kgignatyev.fss.service.security.SecurityPolicy
import com.kgignatyev.fss.service.security.User
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface UsersRepo: CrudRepository<User, String>, JpaSpecificationExecutor<User> {
    fun findByJwtSub(sub: String): Optional<User>
}

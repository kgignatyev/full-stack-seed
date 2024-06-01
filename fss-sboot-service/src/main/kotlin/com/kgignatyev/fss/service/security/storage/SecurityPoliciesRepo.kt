package com.kgignatyev.fss.service.security.storage

import com.kgignatyev.fss.service.security.SecurityPolicy
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
interface SecurityPoliciesRepo:CrudRepository<SecurityPolicy, String>, JpaSpecificationExecutor<SecurityPolicy> {
    fun findByUserId(userId: String): List<SecurityPolicy>
    fun deleteByUserId(userId: String)
}

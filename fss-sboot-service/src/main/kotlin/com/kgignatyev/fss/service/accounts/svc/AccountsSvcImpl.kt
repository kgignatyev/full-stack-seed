package com.kgignatyev.fss.service.accounts.svc

import com.kgignatyev.fss.service.common.data.SearchResult
import com.kgignatyev.fss.service.accounts.AccountsSvc
import com.kgignatyev.fss.service.accounts.Account
import com.kgignatyev.fss.service.accounts.AccountEvent
import com.kgignatyev.fss.service.accounts.storage.AccountsRepo
import com.kgignatyev.fss.service.common.events.CrudEventType
import com.kgignatyev.fss.service.security.SecuritySvc
import com.kgignatyev.fss.service.security.UserSvc
import jakarta.annotation.Resource
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class AccountsSvcImpl( val _accountCrudRepo:AccountsRepo, val publisher: ApplicationEventPublisher): AccountsSvc, CrudRepository<Account, String> by _accountCrudRepo{

    @Resource
    lateinit var securitySvc: SecuritySvc

    override fun search(searchExpr: String, sortExpr:String, offset: Long, limit: Int): SearchResult<Account> {
        return searchImpl(searchExpr, sortExpr, offset, limit, _accountCrudRepo)
    }

    @Transactional
    override fun <S : Account?> save(entity: S & Any): S & Any {
        securitySvc.ensureCurrentUserIsStored()
        entity.ownerId = securitySvc.getCallerInfo().currentUser.id
        val a = _accountCrudRepo.save(entity)
        val eventType = if( entity.id == "") CrudEventType.CREATED else CrudEventType.UPDATED
        publisher.publishEvent(AccountEvent(a,eventType))
        return a
    }

    override fun findById(id: String): Optional<Account> {
        return if( "my" == id) {
            val userId = securitySvc.getCallerInfo().currentUser.id
            val accounts = _accountCrudRepo.findByOwnerId(userId)
            if(accounts.isEmpty()) {
                Optional.empty()
            } else {
                if( accounts.size>1){
                    throw IllegalStateException("Multiple accounts found for user $userId")
                }
                Optional.of(accounts[0])
            }
        } else {
            _accountCrudRepo.findById(id)
        }
    }
}

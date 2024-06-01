package com.kgignatyev.fss.service.job.svc

import com.kgignatyev.fss.service.common.data.SearchResult
import com.kgignatyev.fss.service.common.events.CrudEventType
import com.kgignatyev.fss.service.job.JobEvent
import com.kgignatyev.fss.service.job.JobEventEvent
import com.kgignatyev.fss.service.job.JobEventService
import com.kgignatyev.fss.service.job.storage.JobEventsRepo
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@Service
@Transactional
class JobEventServiceImpl( val jobEventsRepo: JobEventsRepo, val publisher: ApplicationEventPublisher): JobEventService, CrudRepository<JobEvent, String> by jobEventsRepo {
    override fun search(searchExpr: String, sortExpr: String, offset: Long, limit: Int): SearchResult<JobEvent> {
        return searchImpl(searchExpr, sortExpr, offset, limit, jobEventsRepo)
    }

    override fun <S : JobEvent?> save(entity: S & Any): S & Any {
        val a = jobEventsRepo.save(entity)
        publisher.publishEvent(JobEventEvent(a, CrudEventType.CREATED))
        return a
    }
}

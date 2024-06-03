package com.kgignatyev.fss.service.job.svc

import com.kgignatyev.fss.service.accounts.AccountEvent
import com.kgignatyev.fss.service.accounts.AccountsSvc
import com.kgignatyev.fss.service.common.data.SearchResult
import com.kgignatyev.fss.service.common.events.CrudEventType
import com.kgignatyev.fss.service.job.Job
import com.kgignatyev.fss.service.job.JobEventEvent
import com.kgignatyev.fss.service.job.JobEventService
import com.kgignatyev.fss.service.job.JobService
import com.kgignatyev.fss.service.job.storage.JobsRepo
import com.kgignatyev.fss.service.security.SecuritySvc
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobEventType
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobStatus
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Service

@Service
@Transactional
class JobServiceImpl(val _jobsRepo: JobsRepo, val jobEventService: JobEventService, val accountsSvc: AccountsSvc,
                     val securitySvc: SecuritySvc): JobService, CrudRepository<Job, String> by _jobsRepo{

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun search(searchExpr: String, sortExpr:String,  offset: Long, limit: Int): SearchResult<Job> {
        return searchImpl(searchExpr,sortExpr, offset, limit, _jobsRepo)
    }


    override fun <S : Job?> save(job: S & Any): S & Any {
        val a = accountsSvc.findById(job.accountId).get()
        if( job.accountId == "my") {
            job.accountId = a.id
        }
        securitySvc.checkCurrentUserAuthorized(a, "update")
        return _jobsRepo.save(job)
    }


    @ApplicationModuleListener
    @Transactional
    fun onAccountEvent(event: AccountEvent){
        when(event.eventType){
            CrudEventType.DELETED -> {
                val jobs = _jobsRepo.findByAccountId( event.data.id )
                logger.info("Deleting ${jobs.size} jobs")
                jobs.forEach {
                    jobEventService.deleteByJobId(it.id)
                    _jobsRepo.delete(it)
                }
            }
            else -> {}
        }
    }

    @ApplicationModuleListener
    @Transactional
    fun onJobEventEvent( evt: JobEventEvent): Job{
        logger.info("Updating job status: ${evt.data.jobId}")
        val job = _jobsRepo.findById(evt.data.jobId).orElseThrow { IllegalArgumentException("Job not found") }
        job.status = when(evt.data.eventType){
            V1JobEventType.APPLIED -> V1JobStatus.APPLIED
            V1JobEventType.APPLIED_VIA_AGENCY -> V1JobStatus.APPLIED_VIA_AGENCY
            V1JobEventType.INTERVIEW_SCHEDULED -> V1JobStatus.INTERVIEWING
            V1JobEventType.INTERVIEWED -> V1JobStatus.INTERVIEWING
            V1JobEventType.REJECTED -> V1JobStatus.REJECTED_BY_COMPANY
            V1JobEventType.HIRED -> V1JobStatus.HIRED
            V1JobEventType.POSITION_CLOSED -> V1JobStatus.POSITION_CLOSED
            else -> job.status
        }
        return save(job)
    }
}

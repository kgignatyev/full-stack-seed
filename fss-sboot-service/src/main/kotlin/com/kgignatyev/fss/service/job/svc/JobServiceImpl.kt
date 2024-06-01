package com.kgignatyev.fss.service.job.svc

import com.kgignatyev.fss.service.common.data.SearchResult
import com.kgignatyev.fss.service.job.JobService
import com.kgignatyev.fss.service.job.Job
import com.kgignatyev.fss.service.job.storage.JobsRepo
import com.kgignatyev.fss.service.job.JobEventEvent
import jakarta.transaction.Transactional
import org.springframework.data.repository.CrudRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Service

@Service
@Transactional
class JobServiceImpl(val _jobsRepo: JobsRepo): JobService, CrudRepository<Job, String> by _jobsRepo{
    override fun search(searchExpr: String, sortExpr:String,  offset: Long, limit: Int): SearchResult<Job> {
        return searchImpl(searchExpr,sortExpr, offset, limit, _jobsRepo)
    }



    @ApplicationModuleListener
    @Transactional
    fun onJobEvent(event: JobEventEvent){
        println("Job event: ${event.data.jobId}")
    }
}

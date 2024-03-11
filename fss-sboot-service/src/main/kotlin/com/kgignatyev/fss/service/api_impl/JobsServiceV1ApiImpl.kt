package com.kgignatyev.fss.service.api_impl

import com.kgignatyev.fss.service.data.*
import com.kgignatyev.fss_svc.api.fsssvc.JobsServiceV1Api
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Job
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobListResult
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1SearchRequest
import jakarta.annotation.Resource
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RequestMapping(path = ["/api"])
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"], methods = [RequestMethod.PATCH, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.PUT])
@RestController
class JobsServiceV1ApiImpl:JobsServiceV1Api {

    @Resource
    lateinit var jobsRepo:JobsRepo
    @Resource
    lateinit var searchableJobsRepo: SearchableJobsRepo
    @Resource
    lateinit var conv:ConversionService

    override fun getAllJobs(): ResponseEntity<List<V1Job>> {
        return ok( jobsRepo.findAll().map { conv.convert(it,V1Job::class.java)!! })
    }

    override fun searchJobs(v1SearchRequest: V1SearchRequest): ResponseEntity<V1JobListResult> {
        val r:SearchResult<Job> = searchableJobsRepo.search(v1SearchRequest.searchExpression,v1SearchRequest.pagination.offset, v1SearchRequest.pagination.limit)
        val res = V1JobListResult()
        res.items = r.items.map { conv.convert(it, V1Job::class.java)!! }
        res.listSummary = r.summary.toListSummary()
        return ok( res )
    }

    override fun updateJobById(id: String, v1Job: V1Job): ResponseEntity<V1Job> {
        val j = conv.convert( v1Job,Job::class.java)!!
        val r =  jobsRepo.save( j )
        return ok( conv.convert(r, V1Job::class.java))
    }
}

package com.kgignatyev.fss.service.jobs.data

import com.kgignatyev.fss.service.FssService
import com.kgignatyev.fss.service.job.JobService
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication


class JobsRepoTest {

    @Test
    fun testSearch(){
        val cxt = SpringApplication.run(FssService::class.java)
        val jobsService = cxt.getBean(JobService::class.java)
        val res = jobsService.search("""companyName ilike "%i%" """, "title")
//        val res = sJobsRepo.search("""x""")
        println( "Found: ${res.items}")

    }
}

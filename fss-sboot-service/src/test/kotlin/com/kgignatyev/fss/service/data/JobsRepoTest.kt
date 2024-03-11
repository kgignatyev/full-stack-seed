package com.kgignatyev.fss.service.data

import com.kgignatyev.fss.service.FssService
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication


class JobsRepoTest {

    @Test
    fun testSearch(){
        val cxt = SpringApplication.run(FssService::class.java)
        val sJobsRepo = cxt.getBean(SearchableJobsRepo::class.java)
        val res = sJobsRepo.search("""companyName ilike "%i%" """)
//        val res = sJobsRepo.search("""x""")
        println( "Found: ${res.items}")

    }
}

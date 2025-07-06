package com.kgignatyev.fss.service.acceptance.scalability

import com.kgignatyev.fss_svc.api.fss_client.v1.apis.AccountsServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.JobsServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Job
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1JobStatus
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1Pagination
import com.kgignatyev.fss_svc.api.fss_client.v1.models.V1SearchRequest
import io.cucumber.java.en.When
import jakarta.annotation.Resource
import net.datafaker.Faker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.OffsetDateTime
import kotlin.time.measureTime


class ScalabilitySteps {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Resource
    lateinit var jobApi: JobsServiceV1Api

    @Resource
    lateinit var acntApi: AccountsServiceV1Api

    @When("current user can create {int} jobs in less than {int} seconds")
    fun current_user_can_create_jobs_in_less_than_seconds(numJobs: Int, limitSeconds: Int) {
        val df = Faker()
        val myAcnt = acntApi.getAccountById("my")
        val elapsed = measureTime {
            for (i in 1..numJobs) {
                val dfJ = df.job()
                val j = V1Job("", myAcnt.id, dfJ.title(), "test", V1JobStatus.FOR_REVIEW, "", OffsetDateTime.now())
                jobApi.createJob("my", j)
            }
        }

        val elapsedSeconds = elapsed.inWholeSeconds
        if (elapsedSeconds > limitSeconds) {
            throw RuntimeException("Elapsed time $elapsedSeconds seconds is more than limit $limitSeconds seconds")
        }
    }

    @When("current user searches with {string} it can see page {int} with {int} in less than {int} milliseconds")
    fun current_user_searches_with_it_can_see_page_in_less_than_milliseconds(
        criteria: String,
        pageN: Int,
        pageSize: Int,
        searchTimeMillis: Int
    ) {
        val myAcnt = acntApi.getAccountById("my")
        val effCriteria = "accountId = '${myAcnt.id}' and $criteria"
        val r = V1SearchRequest( effCriteria, "title asc,id asc",V1Pagination(((pageN-1)*pageSize).toLong(), pageSize))
        var nFound = 0
        val elapsed = measureTime {
            val res = jobApi.searchJobs(r)
            nFound = res.listSummary.total.toInt()
        }
        val elapsedMillis = elapsed.inWholeMilliseconds
        logger.info("Search took $elapsedMillis milliseconds, found $nFound jobs")
        if( elapsedMillis > searchTimeMillis) {
            throw RuntimeException("Search took $elapsedMillis milliseconds, which is more than $searchTimeMillis limit, found $nFound jobs")
        }
    }
}

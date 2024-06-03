package com.kgignatyev.fss.service.acceptance.job

import com.kgignatyev.fss.service.acceptance.TestsContext
import com.kgignatyev.fss_svc.api.fss_client.v1.apis.JobsServiceV1Api
import com.kgignatyev.fss_svc.api.fss_client.v1.models.*
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeExactly
import jakarta.annotation.Resource
import java.time.OffsetDateTime


class JobSteps {

    @Resource
    lateinit var jobApi: JobsServiceV1Api

    @Then("current user can create a new job {string}")
    fun current_user_can_create_a_new_job(jobTitle: String) {
        val j = V1Job( "", "my", jobTitle, "",V1JobStatus.FOR_REVIEW,"", OffsetDateTime.now())
        val createdJob = jobApi.createJob("my", j)
        TestsContext.currentJob = createdJob
    }

    @Given("current user can find {string}")
    fun current_user_can_find(jobTitle: String) {
        val r = V1SearchRequest( V1Pagination(1, 10), "title = '$jobTitle'", "title asc")
        val jobs = jobApi.searchJobs(r).items
        jobs.size shouldBeExactly 1
        TestsContext.currentJob = jobs[0]
    }

    @Then("current user can update current job notes")
    fun current_user_can_update_current_job_notes() {
        val j = TestsContext.currentJob!!
        val updatedJob = j.copy( notes = "new notes ${OffsetDateTime.now()}")
        val jobFromServer = jobApi.updateJobById(j.id, updatedJob)
        jobFromServer.notes shouldBeEqual updatedJob.notes
        jobFromServer.updatedAt!! shouldBeAfter j.updatedAt!!
    }

    @Then("current user can add event {string} happened on {string} with notes {string}")
    fun current_user_can_add_event_happened_on_with_notes(eventType: String, date: String, notes: String) {
        val j = TestsContext.currentJob!!
        val uniqueNotes = "$notes ${OffsetDateTime.now()}"
        val e = V1JobEvent("", j.id, V1JobEventType.decode(eventType)!!, uniqueNotes, OffsetDateTime.parse("${date}T10:20:00Z"))
        val createdEvent = jobApi.createJobEvent(j.id, e)
        createdEvent.eventType shouldBeEqual e.eventType
        createdEvent.notes shouldBeEqual uniqueNotes
    }

    @Then("check that job has status: {string}")
    fun check_that_job_has_status(string: String) {
        val j = TestsContext.currentJob!!
        val jobOnServer = jobApi.getJobById(j.id)
        jobOnServer.status shouldBeEqual V1JobStatus.decode(string)!!
        TestsContext.currentJob = jobOnServer
    }

}

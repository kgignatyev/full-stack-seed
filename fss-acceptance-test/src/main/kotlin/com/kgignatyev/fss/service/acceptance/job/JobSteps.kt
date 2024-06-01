package com.kgignatyev.fss.service.acceptance.job

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then


class JobSteps {

    @Then("current user can create a new job {string}")
    fun current_user_can_create_a_new_job(string: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Given("current user can find {string}")
    fun current_user_can_find(string: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("current user can see updated job {string} notes")
    fun current_user_can_see_updated_job_notes(string: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("current user can add event {string} happened on {string} with notes {string}")
    fun current_user_can_add_event_happened_on_with_notes(eventType: String?, date: String?, notes: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("check that job has status: {string}")
    fun check_that_job_has_status(string: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

}

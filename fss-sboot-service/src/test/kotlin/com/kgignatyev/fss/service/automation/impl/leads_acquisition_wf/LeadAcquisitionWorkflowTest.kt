package com.kgignatyev.fss.service.automation.impl.leads_acquisition_wf

import com.kgignatyev.fss.service.automation.impl.common.EventType
import com.kgignatyev.fss.service.automation.impl.common.NotificationActivity
import com.kgignatyev.fss.service.automation.impl.leads_acquisition_wf.LeadsAcquisitionWorkflow.Companion.QUEUE_LEADS_ACQUISITION_WF
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.testing.TestWorkflowEnvironment
import io.temporal.worker.Worker
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LeadsHandlingActivityTestImpl : LeadsHandlingActivity {

    var numLeadsToSimulate = 10
    var failRequests = false

    var leadsDeduplicated = false
    var leadsMatched = false

    fun reset(){
        numLeadsToSimulate = 10
        failRequests = false
        leadsDeduplicated = false
    }

    override fun acquireLeads(sourceId: String): LeadAcquisitionData {
        if (failRequests) {
            throw Exception("test exception")
        }
        return LeadAcquisitionData( numLeadsToSimulate , UUID.randomUUID().toString())
    }

    override fun deduplicateLeads(groupId: String): Int {
        println("deduplicate leads: $groupId")
        leadsDeduplicated = true
        return numLeadsToSimulate/2
    }

    override fun matchAgainstSeekerPreferences(groupId: String) {
        println("match against seeker preferences: $groupId")
        leadsMatched = true
    }

}

class NotificationActivityTestImpl : NotificationActivity {

    val receivedData:MutableList<Pair<EventType, String>> = mutableListOf()

    override fun notifyAboutEvent(
        eventType: EventType,
        message: String
    ) {
        receivedData.add(Pair(eventType, message))
        println("eventType: $eventType, message: $message")
    }

    fun reset() {
        receivedData.clear()
    }

}

class LeadAcquisitionWorkflowTest {

    private var testEnv: TestWorkflowEnvironment? = null
    private var worker: Worker? = null
    private var notificationWorker: Worker? = null
    private var client: WorkflowClient? = null
    private var leadGrabberActivity: LeadsHandlingActivityTestImpl = LeadsHandlingActivityTestImpl()
    private var notificationActivity: NotificationActivityTestImpl = NotificationActivityTestImpl()

    @BeforeEach
    fun setup() {
        leadGrabberActivity.reset()
        notificationActivity.reset()
        testEnv = TestWorkflowEnvironment.newInstance()
        worker = testEnv!!.newWorker(LeadsAcquisitionWorkflow.QUEUE_LEADS_ACQUISITION_WF)
        notificationWorker = testEnv!!.newWorker(NotificationActivity.QUEUE_NOTIFICATIONS)
        client = testEnv!!.workflowClient
        worker!!.registerWorkflowImplementationTypes(LeadsAcquisitionWorkflowImpl::class.java)
        worker!!.registerActivitiesImplementations(leadGrabberActivity)
        notificationWorker!!.registerActivitiesImplementations(notificationActivity)
        testEnv!!.start()
    }

    @AfterEach
    fun teardown() {
        testEnv!!.close()
    }


    @Test
    fun testLeadAcquisitionWorkflow() {
        val workflow: LeadsAcquisitionWorkflow = client!!.newWorkflowStub<LeadsAcquisitionWorkflow>(
            LeadsAcquisitionWorkflow::class.java,
            WorkflowOptions.newBuilder()
                .setTaskQueue(QUEUE_LEADS_ACQUISITION_WF)
                .build()
        )
        workflow.acquireLeads("LinkedIn")
        assertTrue { leadGrabberActivity.leadsDeduplicated }
        assertTrue { leadGrabberActivity.leadsMatched }
        assertEquals( 1, notificationActivity.receivedData.size)

    }

}

package com.kgignatyev.fss.service.automation.impl

import com.kgignatyev.fss.service.automation.AutomationSvc
import com.kgignatyev.fss.service.automation.WorkflowInfo
import io.temporal.client.WorkflowClient
import io.temporal.api.workflowservice.v1.ListWorkflowExecutionsRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AutomationSvcImpl: AutomationSvc {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired(required = false)
    lateinit var workflowClient: WorkflowClient

    override fun listWorkflows(): List<WorkflowInfo> {
        if( ! ::workflowClient.isInitialized  ) {
            return emptyList<WorkflowInfo>()
        }
        val request = ListWorkflowExecutionsRequest.newBuilder()
            .setNamespace("default")
            .build()

        val response = workflowClient.workflowServiceStubs
            .blockingStub()
            .listWorkflowExecutions(request)

        return response.executionsList.map { execution ->
            WorkflowInfo(
                execution.execution.workflowId,
                execution.execution.javaClass.simpleName,
                execution.status.name
            )
        }
    }
}

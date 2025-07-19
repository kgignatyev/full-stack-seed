package com.kgignatyev.fss.service.automation

import com.kgignatyev.fss_svc.api.fsssvc.AutomationServiceV1Api
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1StartWorkflowRequest
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Status
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1WorkflowInfo
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/api"])
@CrossOrigin(
    origins = ["*"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.PATCH, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.PUT]
)
@RestController
class AutomationServiceV1ApiImpl(
    @Qualifier("mvcConversionService") val conv: ConversionService,
    val automationSvc: AutomationSvc
) : AutomationServiceV1Api {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun startWorkflow(v1StartWorkflowRequest: @Valid V1StartWorkflowRequest): ResponseEntity<V1Status> {
        try {
            automationSvc.startWorkflow(v1StartWorkflowRequest.wfType)
            return ok(V1Status("OK"))
        } catch (e: Exception) {
            logger.error("Failed to start workflow", e)
            return ResponseEntity.badRequest().body(
                V1Status("ERROR").description(e.message)
            )
        }
    }

    override fun listWorkflows(): ResponseEntity<List<V1WorkflowInfo>> {
//        logger.warn("List workflows method is not properly implemented!")
        val workflows = automationSvc.listWorkflows()
        return ok(
            workflows.map { wf ->
                conv.convert(wf, V1WorkflowInfo::class.java)!!
            }
        )
    }


}

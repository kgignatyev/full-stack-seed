package com.kgignatyev.fss.service.automation.impl.leads_acquisition_wf

import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface LeadAcquisitionWorkflow {
    @WorkflowMethod
    fun acquireLeads( sourceId: String )
}



class LeadAcquisitionWorkflowImpl: LeadAcquisitionWorkflow {
    override fun acquireLeads(sourceId: String) {
        TODO("Not yet implemented")
    }

}

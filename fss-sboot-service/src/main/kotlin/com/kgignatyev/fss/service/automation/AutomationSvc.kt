package com.kgignatyev.fss.service.automation



interface AutomationSvc {
    fun listWorkflows(): List<WorkflowInfo>
    fun startWorkflow(wfType: String)
}

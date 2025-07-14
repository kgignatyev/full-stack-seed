package com.kgignatyev.fss.service.automation.impl.payments_wf

import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

/**
 * this workflow runs indefinitely long till stopped
 */
@WorkflowInterface
interface PaymentsWorkflow {

    @WorkflowMethod
    fun runPaymentsWorkflow(accountId: String){

    }

    @SignalMethod
    fun paymentDetailsUpdated()

}

package com.kgignatyev.fss.service.automation

import java.time.OffsetDateTime


data class WorkflowInfo(val id: String, val runId:String, val wfType:String, val status:String,
                        val startTime: OffsetDateTime, val endTime: OffsetDateTime)

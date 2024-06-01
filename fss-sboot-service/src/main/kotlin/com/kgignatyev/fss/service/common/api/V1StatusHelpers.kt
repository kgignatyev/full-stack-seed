package com.kgignatyev.fss.service.common.api

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Status
import org.springframework.http.ResponseEntity


object V1StatusHelpers {
    const val STATUS_ERROR = "ERROR"
    const val STATUS_OK = "OK"
    val OK = V1Status().responseCode(STATUS_OK)
    val OK_ENTITY = ResponseEntity.ok(OK)
}

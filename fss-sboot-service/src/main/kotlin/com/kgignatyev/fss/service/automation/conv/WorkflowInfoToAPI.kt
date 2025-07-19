package com.kgignatyev.fss.service.automation.conv

import com.kgignatyev.fss.service.automation.WorkflowInfo
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1WorkflowInfo
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class WorkflowInfoToAPI: Converter<WorkflowInfo, V1WorkflowInfo> {
    override fun convert(s: WorkflowInfo): V1WorkflowInfo {
       return V1WorkflowInfo()
           .wfType( s.wfType )
           .status( s.status )
           .id( s.id )

    }
}

package com.kgignatyev.fss.service.security.conv

import com.kgignatyev.fss.service.security.SecurityPolicy
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1SecurityPolicy
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class Policy2API:Converter<SecurityPolicy, V1SecurityPolicy> {
    override fun convert(source: SecurityPolicy): V1SecurityPolicy {
        val target = V1SecurityPolicy()
        target.id = source.id
        target.policyExpression = "${source.userId},${source.policy}"
        target.userId = source.userId
        return target
    }
}

package com.kgignatyev.fss.service.conv

import com.kgignatyev.fss.service.data.Company
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Company
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class CompanyToApi: Converter<Company, V1Company> {
    override fun convert(from: Company): V1Company {
        return V1Company().id(from.id).name(from.name)
    }
}

@Component
class ApiToCompany: Converter<V1Company, Company> {
    override fun convert(from: V1Company): Company {
        val r = Company()
        r.id = from.id
        r.name = from.name
        return r
    }
}

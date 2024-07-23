package com.kgignatyev.fss.service.company.conv

import com.kgignatyev.fss.service.company.Company
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Company
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class CompanyToApi: Converter<Company, V1Company> {
    override fun convert(from: Company): V1Company {
        return V1Company().id(from.id).name(from.name)
            .source(from.source).banned(from.banned)
            .notes(from.notes)
            .createdAt(from.createdAt).updatedAt(from.updatedAt)
    }
}

@Component
class ApiToCompany: Converter<V1Company, Company> {
    override fun convert(from: V1Company): Company {
        val r = Company()
        r.id = from.id
        r.name = from.name
        r.banned = from.banned
        r.notes = from.notes
        r.source = from.source
        r.createdAt = from.createdAt
        if( from.updatedAt != null) {
            r.updatedAt = from.updatedAt
        }
        return r
    }
}

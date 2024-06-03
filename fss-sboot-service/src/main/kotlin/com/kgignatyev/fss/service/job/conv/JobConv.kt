package com.kgignatyev.fss.service.job.conv

import com.kgignatyev.fss.service.job.Job
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1Job
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ApiToJob : Converter<V1Job, Job> {
    override fun convert(s: V1Job): Job? {
        val j = Job()
        j.id = s.id
        j.sourceId = s.sourceId
        j.accountId = s.accountId
        j.title = s.title
        j.status = s.status
        j.notes = s.notes
        if( s.companyName != null )
            j.companyName = s.companyName

        if( s.companyResponse != null )
          j.companyResponse = s.companyResponse
        j.createdAt = s.createdAt
        if( s.updatedAt != null) {
            j.updatedAt = s.updatedAt
        }
        return j
    }

}


@Component
class Job2Api : Converter<Job, V1Job> {
    override fun convert(s: Job): V1Job? {
        val j = V1Job()
        j.id = s.id
        j.accountId = s.accountId
        j.sourceId = s.sourceId
        j.title = s.title
        j.status = s.status
        j.notes = s.notes
        j.companyName = s.companyName
        j.companyResponse = s.companyResponse
        j.createdAt = s.createdAt
        j.updatedAt = s.updatedAt
        return j
    }

}

package com.kgignatyev.fss.service.job

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1CompanyResponse
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobStatus
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "jobs_jobs")
class Job {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "jobs_id")
    var id:String = ""
    var title  = ""
    @Column(name = "description")
    var description: String = ""
    @Column(name = "company_name" )
    var companyName  = ""
    @Column(name = "source_id")
    var sourceId = ""
    @Column(name = "source")
    var source = ""
    @Column(name = "notes")
    var notes = ""
    var createdAt = OffsetDateTime.now()
    var updatedAt = createdAt
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status:V1JobStatus = V1JobStatus.FOR_REVIEW
    @Enumerated(EnumType.STRING)
    @Column(name = "company_response")
    var companyResponse:V1CompanyResponse  = V1CompanyResponse.NONE
}

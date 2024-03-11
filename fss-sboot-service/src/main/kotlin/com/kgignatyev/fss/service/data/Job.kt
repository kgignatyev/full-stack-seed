package com.kgignatyev.fss.service.data

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.*
import jakarta.persistence.*
import org.springframework.stereotype.Indexed
import java.time.OffsetDateTime

@Entity
@Table(name = "jobs", indexes = arrayOf(
    Index(name="idx_jobs_title", columnList = "title", unique = false),
    Index(name="idx_jobs_company_name", columnList = "company_name", unique = false),
))
class Job {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    var id:String = ""
    var title  = ""
    @Column(name = "company_name" ,  columnDefinition = "VARCHAR(100)")
    var companyName  = ""
    @Column(name = "source_id", columnDefinition = "VARCHAR(40)")
    var sourceId = ""
    @Column(name = "source", columnDefinition = "VARCHAR(40)")
    var source = ""
    @Column(name = "notes", columnDefinition = "VARCHAR(256)")
    var notes = ""
    var createdAt = OffsetDateTime.now()
    var updatedAt = createdAt
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    var status:V1JobStatus = V1JobStatus.FOR_REVIEW
    @Enumerated(EnumType.STRING)
    @Column(name = "company_response", columnDefinition = "VARCHAR(20)")
    var companyResponse:V1CompanyResponse  = V1CompanyResponse.NONE
}

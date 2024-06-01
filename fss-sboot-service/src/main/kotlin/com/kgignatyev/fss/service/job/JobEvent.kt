package com.kgignatyev.fss.service.job

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobEventType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "job_events", indexes = arrayOf(
    Index(name="idx_job_events_job_id", columnList = "job_id", unique = false),
))
class JobEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    var id:String = ""
    @Column(name = "job_id")
    var jobId  = ""
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type",)
    var eventType: V1JobEventType = V1JobEventType.OTHER
    @Column(name = "notes")
    var notes: String = ""
    var createdAt: OffsetDateTime = OffsetDateTime.now()
}

package com.kgignatyev.fss.service.job

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1JobEventType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "job_events_jevt")
class JobEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "jevt_id")
    var id:String = ""
    @Column(name = "jobs_id")
    var jobId  = ""
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type",)
    var eventType: V1JobEventType = V1JobEventType.OTHER
    @Column(name = "notes")
    var notes: String = ""
    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: OffsetDateTime = OffsetDateTime.now()
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
}

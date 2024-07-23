package com.kgignatyev.fss.service.company

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1YN
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime


@Entity
@Table(name = "companies_cmpn")
class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cmpn_id")
    var id: String = ""
    @Column(name = "acnt_id")
    var accountId: String = ""
    var name = ""
    @Column(name = "source")
    var source: String = ""

    @Enumerated(EnumType.STRING)
    var banned: V1YN = V1YN.N
    var notes: String = ""

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: OffsetDateTime = OffsetDateTime.now()

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime = createdAt
}

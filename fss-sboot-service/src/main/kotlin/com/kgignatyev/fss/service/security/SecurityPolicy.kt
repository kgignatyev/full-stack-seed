package com.kgignatyev.fss.service.security

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "security_policies_spls")
class SecurityPolicy {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "spls_id")
    var id:String = ""

    @Column(name = "usrs_id")
    var userId:String = ""
    var name:String = ""
    var policy:String = ""
    var createdAt = OffsetDateTime.now()
    var updatedAt = createdAt
}

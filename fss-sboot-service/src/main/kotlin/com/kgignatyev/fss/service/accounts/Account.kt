package com.kgignatyev.fss.service.accounts

import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1YN
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "accounts_acnt")
class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "acnt_id")
    var id = ""
    var name  = ""
    var email = ""
    var notes:String? = ""
    @Column(name = "owner_id")
    var ownerId  = ""
    var active = V1YN.Y
    var created_at = OffsetDateTime.now()
    var updated_at  = created_at
}

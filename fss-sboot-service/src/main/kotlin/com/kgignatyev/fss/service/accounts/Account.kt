package com.kgignatyev.fss.service.accounts

import com.kgignatyev.fss.service.security.Securable
import com.kgignatyev.fss.service.security.SecurableType
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1YN
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "accounts_acnt")
class Account:Securable {
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
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt:OffsetDateTime? = OffsetDateTime.now()
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt:OffsetDateTime?  = createdAt

    override fun type(): SecurableType {
        return SecurableType.ACCOUNT
    }

    override fun id(): String {
        return id
    }

    override fun accountId(): String {
        return id
    }

    override fun ownerId(): String {
        return ownerId
    }
}

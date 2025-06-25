package com.kgignatyev.fss.service.security

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime


@Entity
@Table(name = "users_usrs")
class User:Securable {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "usrs_id")
    var id:String = ""
    @Column(name = "jwt_sub")
    var jwtSub:String = ""
    var name:String = ""
    var email:String = ""
    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt:OffsetDateTime = OffsetDateTime.now()
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt:OffsetDateTime  = createdAt
    override fun type(): SecurableType {
        return SecurableType.USER
    }

    override fun id(): String {
        return this.id
    }

    override fun accountId(): String {
        return "-"
    }

    override fun ownerId(): String {
        return "-"
    }

    override fun toString(): String {
        return "User(id='" + id + "', jwtSub='" + jwtSub + "', name='" + name + "', email='" + email + "', createdAt=" + createdAt + ", updatedAt=" + updatedAt + ")"
    }
}

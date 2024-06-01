package com.kgignatyev.fss.service.security

import jakarta.persistence.*
import java.time.OffsetDateTime


@Entity
@Table(name = "users_usrs")
class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "usrs_id")
    var id:String = ""
    @Column(name = "jwt_sub")
    var jwtSub:String = ""
    var name:String = ""
    var email:String = ""
    var createdAt = OffsetDateTime.now()
    var updatedAt = createdAt
}

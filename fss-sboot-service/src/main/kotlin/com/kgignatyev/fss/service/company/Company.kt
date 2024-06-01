package com.kgignatyev.fss.service.company

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    var id:String = ""
    var name  = ""
}

package com.kgignatyev.fss.service.security

enum class SecurableType( val tName:String) {
    ACCOUNT("Account"),
    JOB("Job"),
    USER("User"),
}

interface Securable {
    fun type(): SecurableType
    fun id(): String
    fun accountId(): String
    fun ownerId(): String
}

package com.kgignatyev.fss.service.common.api

import org.springframework.http.ResponseEntity
import java.util.*


object APIHelpers {

    fun <I,O> ofOptional( v: Optional<I>, f: (I)-> O): ResponseEntity<O> {
        return if (v.isEmpty){
            ResponseEntity.notFound().build()
        }else {
            ResponseEntity.ok(f(v.get()))
        }
    }
}

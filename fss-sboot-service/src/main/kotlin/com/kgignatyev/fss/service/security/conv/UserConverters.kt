package com.kgignatyev.fss.service.security.conv

import com.kgignatyev.fss.service.security.User
import com.kgignatyev.fss_svc.api.fsssvc.v1.model.V1User
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class User2API:Converter<User, V1User> {
    override fun convert(source: User): V1User {
        val target = V1User()
        target.id = source.id
        target.email = source.email
        target.name = source.name
        target.jwtSub = source.jwtSub
        target.createdAt = source.createdAt
        target.updatedAt = source.updatedAt
        return target
    }
}

@Component
class API2User:Converter<V1User, User> {
    override fun convert(source: V1User): User {
       val u = User()
        u.id = source.id
        u.email = source.email?: ""
        u.name = source.name
        u.jwtSub = source.jwtSub?: ""
        u.createdAt = source.createdAt
        u.updatedAt = source.updatedAt
        return u
    }
}

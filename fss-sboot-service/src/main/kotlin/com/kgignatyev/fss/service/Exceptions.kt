package com.kgignatyev.fss.service



class UnauthorizedException(msg:String, cause:Throwable):Exception(msg,cause){
    constructor(msg: String):this(msg,Exception(""))
}

class ValidationException( msg: String):Exception(msg)

class BadRequestException(msg: String):Exception(msg)


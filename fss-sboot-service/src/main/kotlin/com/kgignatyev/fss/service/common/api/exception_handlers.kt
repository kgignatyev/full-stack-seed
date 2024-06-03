package com.kgignatyev.fss.service.common.api

import com.kgignatyev.fss.service.UnauthorizedException
import io.micrometer.tracing.Tracer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException
import java.sql.SQLException


@ControllerAdvice
class ExceptionControllerAdvices {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var tracer: Tracer

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorizedException(exception: UnauthorizedException): ResponseEntity<Any> {
        logger.error("UnauthorizedException: ${exception.message}", exception)
        return ResponseEntity("${exception.message}", HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(
        value = [IllegalStateException::class, IllegalArgumentException::class,
            Exception::class, RuntimeException::class, NoHandlerFoundException::class,
            DataIntegrityViolationException::class, SQLException::class]
    )
    fun ourExceptionsHandler(exception: Exception): ResponseEntity<Any> {
        logger.error("Exception: ${exception.message}", exception)
        val status = when (exception) {
            is IllegalStateException -> HttpStatus.PRECONDITION_FAILED
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val message = when (exception) {
            is SQLException -> "DB Error"
            is DataIntegrityViolationException -> "Data Integrity Violation"
            else -> "${exception.javaClass.name}: ${exception.message ?: " without message"}"
        }
        val correlationID = tracer.currentSpan()?.context()?.traceId() ?: "no-trace-id"
        return ResponseEntity("$message - $correlationID", status)
    }
}




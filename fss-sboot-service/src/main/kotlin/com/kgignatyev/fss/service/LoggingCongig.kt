package com.kgignatyev.fss.service

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class LogSupportFilter :OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            MDC.put("requestId", request.getHeader("X-Request-ID"))
            MDC.put("cid", request.getHeader("cid"))
            filterChain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }
}

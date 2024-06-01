package com.kgignatyev.fss.service.acceptance


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties
open class FssSvcTestApp

fun main(args: Array<String>) {
    SpringApplication.run(FssSvcTestApp::class.java, *args)
}

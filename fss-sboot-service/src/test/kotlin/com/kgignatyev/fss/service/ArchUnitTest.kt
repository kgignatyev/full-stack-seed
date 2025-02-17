package com.kgignatyev.fss.service

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule

import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service
import org.springframework.test.context.event.annotation.BeforeTestClass

class ArchUnitTest {


    @Test
    fun architectureTest() {

        val servicesRule: ArchRule = classes().that().areAnnotatedWith(Service::class.java)
            .should().haveSimpleNameEndingWith("SvcImpl")
        servicesRule.check(serviceClasses)
    }

    companion object {
        private lateinit var serviceClasses: JavaClasses
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            serviceClasses = ClassFileImporter().importPackages("com.kgignatyev.fss")
        }
    }
}

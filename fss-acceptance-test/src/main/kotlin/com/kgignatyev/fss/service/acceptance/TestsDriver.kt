package com.kgignatyev.fss.service.acceptance


import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.junit.Cucumber
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest

@RunWith(Cucumber::class)
class CucumberTestsDriver() {

    private val logger = LoggerFactory.getLogger(CucumberTestsDriver::class.java)

    @Before
    fun setUp(scenario: Scenario) {
        logger.info(">>>> Spring Context Initialized")
    }


}

@CucumberContextConfiguration
@SpringBootTest(classes = [FssSvcTestApp::class])
class CucumberSpringConfiguration

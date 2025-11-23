package com.kgignatyev.fss.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.modulith.Modulithic

@SpringBootApplication()
@EnableJpaRepositories()
@EnableCaching
@Modulithic( //
    sharedModules = [
//        "com.kgignatyev.fss.service.common.storage",
                    ],
    useFullyQualifiedModuleNames = true)
class FssService {

    companion object{

        lateinit var cxt:ConfigurableApplicationContext

        private var om: ObjectMapper? = null

        fun om(): ObjectMapper {
            if (om == null) {
                om = cxt.getBean(ObjectMapper::class.java)
            }
            return om!!
        }

        @JvmStatic
        fun main(args: Array<String>) {
            cxt = SpringApplication.run(FssService::class.java, *args)
        }


    }
}

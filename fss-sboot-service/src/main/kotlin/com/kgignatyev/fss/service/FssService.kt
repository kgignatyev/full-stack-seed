package com.kgignatyev.fss.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
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

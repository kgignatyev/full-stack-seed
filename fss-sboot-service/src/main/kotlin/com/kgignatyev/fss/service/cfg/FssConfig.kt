package com.kgignatyev.fss.service.cfg

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FssConfig {

    @Bean
    fun kotlinModule(): KotlinModule {
        return KotlinModule.Builder().build() // Or customize with specific KotlinFeature settings
    }

}

package com.kgignatyev.fss.service.cfg

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.module.kotlin.KotlinModule


@Configuration
class FssConfig {

    @Bean
    fun kotlinModule(): KotlinModule {
        return KotlinModule.Builder().build() // Or customize with specific KotlinFeature settings
    }

}

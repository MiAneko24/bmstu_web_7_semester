package com.mianeko.fuzzyinferencesystemsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@OpenAPIDefinition(
//    servers = [
//        Server(url = Value("custom.server-url").value, description = "Default Server URL"),
//    ]
//)
@SpringBootApplication
class FuzzyInferenceSystemsBackendApplication

fun main(args: Array<String>) {
    runApplication<FuzzyInferenceSystemsBackendApplication>(*args)
}

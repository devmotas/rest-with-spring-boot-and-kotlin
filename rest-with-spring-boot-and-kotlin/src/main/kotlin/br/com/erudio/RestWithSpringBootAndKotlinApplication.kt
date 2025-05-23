package br.com.erudio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.hateoas.config.EnableHypermediaSupport

@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
@SpringBootApplication
@EntityScan("br.com.erudio.model")
@EnableJpaRepositories("br.com.erudio.repository")
class RestWithSpringBootAndKotlinApplication

fun main(args: Array<String>) {
    runApplication<RestWithSpringBootAndKotlinApplication>(*args)

//    val encoders: MutableMap<String, PasswordEncoder> = HashMap()
//    val pbkdf2Encoder =
//        Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
//    encoders["pbkdf2"] = pbkdf2Encoder
//    val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
//    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)
//
//    val result = passwordEncoder.encode("foo-bar")
//    println("My hash $result")
}

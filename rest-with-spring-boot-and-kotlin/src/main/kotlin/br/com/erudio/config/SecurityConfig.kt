package br.com.erudio.config

import br.com.erudio.security.jwt.JwtTokenFilter
import br.com.erudio.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val encoders: MutableMap<String, PasswordEncoder> = HashMap<String, PasswordEncoder>()
        val pbkdf2Encoder =
            Pbkdf2PasswordEncoder(
                "",
                8,
                185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
            )
        encoders["pbkdf2"] = pbkdf2Encoder
        val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)
        return passwordEncoder
    }

    @Bean
    fun authenticationManagerBean(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            authorizeHttpRequests {
                authorize("/auth/signin", permitAll)
                authorize("/auth/refresh/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/api/file/v1/downloadFile/**", permitAll)
                authorize("/api/**", authenticated)
                authorize("/users", denyAll)
            }
        }

        http.addFilterBefore(JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }


}
package com.example.services

import com.example.controller.BookController
import com.example.data.vo.v1.AccountCredentialsVO
import com.example.data.vo.v1.TokenVO
import com.example.repository.UserRepository
import com.example.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class AuthService {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    private val logger = Logger.getLogger(BookController::class.java.name)


    fun signin(data: AccountCredentialsVO): ResponseEntity<*> {
        logger.info("Trying to sign in with username: ${data.username}")
        return try {
            val username = data.username
            val password = data.password
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            val user = repository.findByUsername(username)
                ?: throw UsernameNotFoundException("Username $username not found!")

            val tokenResponse: TokenVO = tokenProvider.createAccessToken(username!!, user.roles)
            ResponseEntity.ok(tokenResponse)

        } catch (ex: AuthenticationException) {
            ResponseEntity.badRequest().body("Invalid username/password supplied")
        }
    }

    fun refreshToken(username: String, refreshToken: String): ResponseEntity<*> {
        logger.info("Trying refresh token to user: ${username}")

        val user = repository.findByUsername(username)

        val tokenResponse: TokenVO = if (user !== null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }

        return ResponseEntity.ok(tokenResponse)
    }

}
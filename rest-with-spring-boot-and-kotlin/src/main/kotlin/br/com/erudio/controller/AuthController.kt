package br.com.erudio.controller

import br.com.erudio.data.vo.v1.AccountCredentialsVO
import br.com.erudio.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var authService: AuthService

    @Operation(summary = "Sign in", description = "Sign in", tags = ["Authentication"])
    @PostMapping(value = ["/signin"])
    fun signin(@RequestBody data: AccountCredentialsVO?): ResponseEntity<*> {
        return if (data!!.username.isNullOrBlank() || data.password.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid username/password supplied")
        else authService.signin(data)
    }

    @Operation(summary = "Refresh token", description = "Refresh token", tags = ["Authentication"])
    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(
        @PathVariable("username") username: String?,
        @RequestHeader("Authorization") refreshToken: String?,
        @RequestBody data: AccountCredentialsVO?
    ): ResponseEntity<*> {
        return if (refreshToken.isNullOrBlank() || username.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
        else authService.refreshToken(username, refreshToken)
    }
}
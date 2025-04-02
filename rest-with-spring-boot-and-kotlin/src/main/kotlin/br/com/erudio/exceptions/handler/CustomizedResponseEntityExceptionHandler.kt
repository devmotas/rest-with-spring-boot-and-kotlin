package br.com.erudio.exceptions.handler

import br.com.erudio.exceptions.ExceptionResponse
import br.com.erudio.exceptions.InvalidJwtAuthenticationException
import br.com.erudio.exceptions.RequiredObjectIsNullException
import br.com.erudio.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handlerAllExceptions(ex: Exception, request: WebRequest):
            ResponseEntity<br.com.erudio.exceptions.ExceptionResponse> {
        val exceptionResponse = br.com.erudio.exceptions.ExceptionResponse(
            Date(), ex.message,
            request.getDescription(false),
        )

        return ResponseEntity<br.com.erudio.exceptions.ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handlerResourceNotFoundException(ex: Exception, request: WebRequest):
            ResponseEntity<br.com.erudio.exceptions.ExceptionResponse> {
        val exceptionResponse = br.com.erudio.exceptions.ExceptionResponse(
            Date(), ex.message,
            request.getDescription(false),
        )

        return ResponseEntity<br.com.erudio.exceptions.ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RequiredObjectIsNullException::class)
    fun handlerBadRequestException(ex: Exception, request: WebRequest):
            ResponseEntity<br.com.erudio.exceptions.ExceptionResponse> {
        val exceptionResponse = br.com.erudio.exceptions.ExceptionResponse(
            Date(), ex.message,
            request.getDescription(false),
        )

        return ResponseEntity<br.com.erudio.exceptions.ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handlerInvalidJwtAuthenticationException(ex: Exception, request: WebRequest):
            ResponseEntity<br.com.erudio.exceptions.ExceptionResponse> {
        val exceptionResponse = br.com.erudio.exceptions.ExceptionResponse(
            Date(), ex.message,
            request.getDescription(false),
        )

        return ResponseEntity<br.com.erudio.exceptions.ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }
}
package com.example.services

import com.example.controller.BookController
import com.example.data.vo.v1.BookVO
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(@field:Autowired var userRepository: UserRepository) : UserDetailsService {

    private val logger = Logger.getLogger(BookController::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Found user with id: $username")

        val user = userRepository.findByUsername(username)

        return user ?: throw ResourceNotFoundException("User with id: $username not found")
    }
}
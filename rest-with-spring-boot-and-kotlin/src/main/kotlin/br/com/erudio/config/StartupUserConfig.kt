// StartupUserConfig.kt
package br.com.erudio.config

import br.com.erudio.model.Permission
import br.com.erudio.model.User
import br.com.erudio.repository.PermissionRepository
import br.com.erudio.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class StartupUserConfig {

    @Bean
    fun createDefaultUser(
        userRepository: UserRepository,
        permissionRepository: PermissionRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
//            val username = "gustavo"
//            val password = "123456"
//            val fullName = "Gustavo Developer"
//
//            if (userRepository.findByUsername(username) == null) {
//                val permission = permissionRepository.findByDescription("USER")
//                    ?: permissionRepository.save(Permission().apply { description = "USER" })
//
//                val user = User().apply {
//                    userName = username
//                    this.fullName = fullName
//                    this.password = passwordEncoder.encode(password)
//                    accountNonExpired = true
//                    accountNonLocked = true
//                    credentialsNonExpired = true
//                    enabled = true
//                    permissions = listOf(permission)
//                }
//
//                userRepository.save(user)
//                println("Usuário '$username' criado com sucesso!")
//            } else {
//                println("Usuário '$username' já existe.")
//            }
        }
    }
}

package com.example.controller

import com.example.model.Person
import com.example.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController{

    @Autowired
    private lateinit var personService: PersonService

    @RequestMapping("/person")
    fun findAll(): List<Person> {
        return personService.findAll()
    }

    @RequestMapping(value = ["/person/{id}"])
    fun findById(
        @PathVariable(value = "id") id: Long,
    ): Person {
        return personService.findById(id)
    }

}
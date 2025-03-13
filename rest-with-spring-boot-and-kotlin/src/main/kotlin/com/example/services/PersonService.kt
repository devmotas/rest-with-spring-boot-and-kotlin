package com.example.services

import com.example.controller.PersonController
import com.example.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {
    private val counter = AtomicLong()
    private val logger = Logger.getLogger(PersonController::class.java.name)


    fun findById(id : Long) : Person {
        logger.info("Found person with id: $id")

        return Person(
            id = counter.incrementAndGet(),
            firstName = "Gustavo",
            lastName = "Mota",
            address = "Sem endereço",
            gender = "Male",
        )
    }

    fun findAll() : List<Person> {
        logger.info("Found all people")

        val people: MutableList<Person> = mutableListOf()
        people.addAll(
            listOf(
                Person(
                    id = counter.incrementAndGet(),
                    firstName = "Gustavo",
                    lastName = "Mota",
                    address = "Sem endereço",
                    gender = "Male",
                ),
                Person(
                    id = counter.incrementAndGet(),
                    firstName = "Pedro",
                    lastName = "Ribeiro",
                    address = "SED",
                    gender = "Male",
                )
            )
        )
        return people
    }
}
package com.example.services

import com.example.controller.PersonController
import com.example.data.vo.v1.PersonVO
import com.example.exceptions.RequiredObjectIsNullException
import com.example.data.vo.v2.PersonVO as PersonVOV2
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.mapper.custom.PersonMapper
import com.example.model.Person
import com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {
    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var personMapper: PersonMapper

    private val logger = Logger.getLogger(PersonController::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people")
        val vos: List<PersonVO> = DozerMapper.parseListObjects(repository.findAll(), PersonVO::class.java)
        for (person in vos) {
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Found person with id: $id")

        val people = repository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Person with id: $id not found")
            }
        val personVO: PersonVO = DozerMapper.parseObject(people, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person: PersonVO?): PersonVO {
        if (person == null ) throw RequiredObjectIsNullException()
        logger.info("Create person: $person")

        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Create person: $person")

        val entity: Person = personMapper.mapVOToEntity(person)
        return personMapper.mapEntityToVO(repository.save(entity))
    }

    fun update(person: PersonVO?): PersonVO {
        if (person == null ) throw RequiredObjectIsNullException()
        logger.info("Update person: $person")
        val entity = repository.findById(person.key)
            .orElseThrow {
                ResourceNotFoundException("Person with id: ${person.key} not found")
            }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.gender = person.gender
        entity.address = person.address

        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long) {
        logger.info("Delete person: $id")
        val entity: Person = repository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Person with id: $id not found")
            }
        repository.delete(entity)
    }
}
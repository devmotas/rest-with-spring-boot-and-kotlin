package br.com.erudio.services

import br.com.erudio.controller.PersonController
import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.exceptions.RequiredObjectIsNullException
import br.com.erudio.data.vo.v2.PersonVO as PersonVOV2
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.mapper.custom.PersonMapper
import br.com.erudio.model.Person
import br.com.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        if (person == null) throw RequiredObjectIsNullException()
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
        if (person == null) throw RequiredObjectIsNullException()
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

    @Transactional
    fun disabledPerson(id: Long): PersonVO {
        logger.info("Disabling a person with id: $id")
        repository.disablePerson(id)

        return findById(id)
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
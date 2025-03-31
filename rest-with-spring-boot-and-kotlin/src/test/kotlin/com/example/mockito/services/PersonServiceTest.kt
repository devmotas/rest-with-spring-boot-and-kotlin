package com.example.mockito.services

import com.example.exceptions.RequiredObjectIsNullException
import com.example.repository.PersonRepository
import com.example.services.PersonService
import com.example.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {
    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository


    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val people = service.findAll()
        assertNotNull(people)
        assertEquals(14, people.size)

        val person1 = people[1]
        assertNotNull(person1)
        assertNotNull(person1.key)
        assertNotNull(person1.links)
        assertTrue(person1.links.toString().contains("/api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test1", person1.address)
        assertEquals("First Name Test1", person1.firstName)
        assertEquals("Last Name Test1", person1.lastName)
        assertEquals("Female", person1.gender)

        val person2 = people[4]
        assertNotNull(person2)
        assertNotNull(person2.key)
        assertNotNull(person2.links)
        assertTrue(person2.links.toString().contains("/api/person/v1/4>;rel=\"self\""))
        assertEquals("Address Test4", person2.address)
        assertEquals("First Name Test4", person2.firstName)
        assertEquals("Last Name Test4", person2.lastName)
        assertEquals("Male", person2.gender)

        val person3 = people[7]
        assertNotNull(person3)
        assertNotNull(person3.key)
        assertNotNull(person3.links)
        assertTrue(person3.links.toString().contains("/api/person/v1/7>;rel=\"self\""))
        assertEquals("Address Test7", person3.address)
        assertEquals("First Name Test7", person3.firstName)
        assertEquals("Last Name Test7", person3.lastName)
        assertEquals("Female", person3.gender)
    }

    @Test
    fun findById() {
        val person = inputObject.mockEntity(2)
        person.id = 2
        `when`(repository.findById(2)).thenReturn(Optional.of(person))

        val result = service.findById(2)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/person/v1/2>;rel=\"self\""))
        assertEquals("Address Test2", result.address)
        assertEquals("First Name Test2", result.firstName)
        assertEquals("Last Name Test2", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(2)
        val persisted = entity.copy()
        persisted.id = 2
        val vo = inputObject.mockVO(2)

        entity.id = 2
        `when`(repository.save(entity)).thenReturn(persisted)

        val result = service.create(vo)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/person/v1/2>;rel=\"self\""))
        assertEquals("Address Test2", result.address)
        assertEquals("First Name Test2", result.firstName)
        assertEquals("Last Name Test2", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(2)
        val persisted = entity.copy()
        persisted.id = 2

        entity.id = 2
        `when`(repository.findById(2)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(2)
        val result = service.update(vo)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/person/v1/2>;rel=\"self\""))
        assertEquals("Address Test2", result.address)
        assertEquals("First Name Test2", result.firstName)
        assertEquals("Last Name Test2", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.update(null)
        }

        val expectedMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(2)
        `when`(repository.findById(2)).thenReturn(Optional.of(entity))
        service.delete(2)
    }
}
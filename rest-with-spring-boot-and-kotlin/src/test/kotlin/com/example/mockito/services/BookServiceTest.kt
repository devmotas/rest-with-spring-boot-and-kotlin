package com.example.mockito.services

import com.example.exceptions.RequiredObjectIsNullException
import com.example.repository.BookRepository
import com.example.services.BookService
import com.example.unittests.mapper.mocks.MockBook
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
class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()
        assertNotNull(books)
        assertEquals(14, books.size)

        val book1 = books[1]
        assertNotNull(book1)
        assertNotNull(book1.key)
        assertNotNull(book1.links)
        assertTrue(book1.links.toString().contains("/api/book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", book1.author)
        assertEquals("Title Test1", book1.title)
        assertEquals(26.0, book1.price)

        val book2 = books[4]
        assertNotNull(book2)
        assertNotNull(book2.key)
        assertNotNull(book2.links)
        assertTrue(book2.links.toString().contains("/api/book/v1/4>;rel=\"self\""))
        assertEquals("Author Test4", book2.author)
        assertEquals("Title Test4", book2.title)
        assertEquals(29.0, book2.price)

        val book3 = books[7]
        assertNotNull(book3)
        assertNotNull(book3.key)
        assertNotNull(book3.links)
        assertTrue(book3.links.toString().contains("/api/book/v1/7>;rel=\"self\""))
        assertEquals("Author Test7", book3.author)
        assertEquals("Title Test7", book3.title)
        assertEquals(32.0, book3.price)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(2)
        book.id = 2
        `when`(repository.findById(2)).thenReturn(Optional.of(book))

        val result = service.findById(2)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/book/v1/2>;rel=\"self\""))
        assertEquals("Author Test2", result.author)
        assertEquals("Title Test2", result.title)
        assertEquals(27.0, result.price)
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
        assertTrue(result.links.toString().contains("/api/book/v1/2>;rel=\"self\""))
        assertEquals("Author Test2", result.author)
        assertEquals("Title Test2", result.title)
        assertEquals(27.0, result.price)
    }

    @Test
    fun createWithNullBook() {
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
        assertTrue(result.links.toString().contains("/api/book/v1/2>;rel=\"self\""))
        assertEquals("Author Test2", result.author)
        assertEquals("Title Test2", result.title)
        assertEquals(27.0, result.price)
    }

    @Test
    fun updateWithNullBook() {
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

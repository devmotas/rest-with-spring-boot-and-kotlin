package com.example.services

import com.example.controller.BookController
import com.example.data.vo.v1.BookVO
import com.example.exceptions.RequiredObjectIsNullException
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.mapper.custom.BookMapper
import com.example.model.Book
import com.example.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {
    @Autowired
    private lateinit var repository: BookRepository

    @Autowired
    private lateinit var bookMapper: BookMapper

    private val logger = Logger.getLogger(BookController::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Finding all books")
        val vos: List<BookVO> = DozerMapper.parseListObjects(repository.findAll(), BookVO::class.java)
        for (book in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): BookVO {
        logger.info("Found book with id: $id")

        val books = repository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Book with id: $id not found")
            }
        val bookVO: BookVO = DozerMapper.parseObject(books, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(book: BookVO?): BookVO {
        if (book == null ) throw RequiredObjectIsNullException()
        logger.info("Create book: $book")

        val entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun createV2(book: BookVO): BookVO {
        logger.info("Create book: $book")

        val entity: Book = bookMapper.mapVOToEntity(book)
        return bookMapper.mapEntityToVO(repository.save(entity))
    }

    fun update(book: BookVO?): BookVO {
        if (book == null ) throw RequiredObjectIsNullException()
        logger.info("Update book: $book")
        val entity = repository.findById(book.key)
            .orElseThrow {
                ResourceNotFoundException("Book with id: ${book.key} not found")
            }

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate

        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun delete(id: Long) {
        logger.info("Delete book: $id")
        val entity: Book = repository.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Book with id: $id not found")
            }
        repository.delete(entity)
    }
}
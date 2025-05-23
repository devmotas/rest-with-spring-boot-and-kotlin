package br.com.erudio.services

import br.com.erudio.controller.BookController
import br.com.erudio.data.vo.v1.BookVO
import br.com.erudio.exceptions.RequiredObjectIsNullException
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.mapper.custom.BookMapper
import br.com.erudio.model.Book
import br.com.erudio.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {
    @Autowired
    private lateinit var repository: BookRepository

    @Autowired
    private lateinit var bookMapper: BookMapper

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<BookVO>

    private val logger = Logger.getLogger(BookController::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        logger.info("Finding all books")
        val book = repository.findAll(pageable)
        val vos = book.map { p -> DozerMapper.parseObject(p, BookVO::class.java) }
        vos.map { p -> p.add(linkTo(BookController::class.java).slash(p.key).withSelfRel()) }
        return assembler.toModel(vos)
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
        if (book == null) throw RequiredObjectIsNullException("It is not allowed to persist a null object!")
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
        if (book == null) throw RequiredObjectIsNullException("It is not allowed to persist a null object!")
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
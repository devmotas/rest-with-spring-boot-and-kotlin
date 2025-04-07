package br.com.erudio.controller

import br.com.erudio.data.vo.v1.BookVO
import br.com.erudio.services.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/book/v1", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
@Tag(name = "Books", description = "Endpoint for manage books")
class BookController {

    @Autowired
    private lateinit var bookService: BookService

    @GetMapping
    @Operation(
        summary = "Find all books", description = "Get all books",
        tags = ["Books"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        array = ArraySchema(
                            schema = Schema(implementation = BookVO::class)
                        )
                    )]
            ),
            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Bad Request",
                responseCode = "400",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Not Found",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Internal Error",
                responseCode = "505",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),

        ]
    )
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String
    ): ResponseEntity<PagedModel<EntityModel<BookVO>>> {
        val sortDirection: Sort.Direction = if ("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC
        else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"))
        return ResponseEntity.ok(bookService.findAll(pageable))
    }

    @GetMapping(value = ["/{id}"])
    @Operation(
        summary = "Finds a book", description = "Finds a book",
        tags = ["Books"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = BookVO::class)

                    )]
            ),
            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Bad Request",
                responseCode = "400",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Not Found",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Internal Error",
                responseCode = "505",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),

        ]
    )
    fun findById(
        @PathVariable(value = "id") id: Long,
    ): BookVO {
        return bookService.findById(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @Operation(
        summary = "Adds a new book", description = "Adds a new book",
        tags = ["Books"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = BookVO::class)

                    )]
            ),
            ApiResponse(
                description = "Bad Request",
                responseCode = "400",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Internal Error",
                responseCode = "505",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),

        ]
    )
    fun createBook(
        @RequestBody book: BookVO
    ): BookVO {
        return bookService.create(book)
    }

//    @PostMapping(value = ["/v2"], consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
//    fun createBookV2(
//        @RequestBody book: BookVOV2
//    ): BookVOV2 {
//        return bookService.createV2(book)
//    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @Operation(
        summary = "Updates a book information", description = "Updates a book information",
        tags = ["Books"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = BookVO::class)

                    )]
            ),
            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Bad Request",
                responseCode = "400",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Not Found",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Internal Error",
                responseCode = "505",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),

        ]
    )
    fun updateBook(
        @RequestBody book: BookVO
    ): BookVO {
        return bookService.update(book)
    }

    @DeleteMapping(value = ["/{id}"])
    @Operation(
        summary = "Deletes a book", description = "Deletes a book",
        tags = ["Books"], responses = [

            ApiResponse(
                description = "No Content",
                responseCode = "204",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Bad Request",
                responseCode = "400",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Unauthorized",
                responseCode = "401",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Not Found",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),
            ApiResponse(
                description = "Internal Error",
                responseCode = "505",
                content = [
                    Content(
                        schema = Schema(implementation = Unit::class)
                    )]
            ),

        ]
    )
    fun deleteBook(
        @PathVariable(value = "id") id: Long,
    ): ResponseEntity<*> {
        bookService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}

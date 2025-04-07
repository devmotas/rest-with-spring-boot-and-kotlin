package br.com.erudio.controller

import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.services.PersonService
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

//@CrossOrigin
@RestController
@RequestMapping("api/person/v1", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
@Tag(name = "People", description = "Endpoint for manage people")
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping
    @Operation(
        summary = "Find all people", description = "Get all people",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        array = ArraySchema(
                            schema = Schema(implementation = PersonVO::class)
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
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String,
    ): ResponseEntity<PagedModel<EntityModel<PersonVO>>> {
        val sortDirection: Sort.Direction = if ("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC
        else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"))
        return ResponseEntity.ok(personService.findAll(pageable))
    }

    @GetMapping(value = ["/findPersonByName/{firstName}"])
    @Operation(
        summary = "Find a person by name", description = "Find a person by name",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        array = ArraySchema(
                            schema = Schema(implementation = PersonVO::class)
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
    fun findPersonByName(
        @PathVariable(value = "firstName") firstName: String,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String,
    ): ResponseEntity<PagedModel<EntityModel<PersonVO>>> {
        val sortDirection: Sort.Direction = if ("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC
        else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"))
        return ResponseEntity.ok(personService.findPersonByName(firstName, pageable))
    }

    @CrossOrigin(origins = ["http://localhost:8080"])
    @GetMapping(value = ["/{id}"])
    @Operation(
        summary = "Finds a person", description = "Finds a person",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = PersonVO::class)

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
    ): PersonVO {
        return personService.findById(id)
    }

    @CrossOrigin(origins = ["http://localhost:8080", "https://erudio.com.br"])
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @Operation(
        summary = "Adds a new person", description = "Adds a new person",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = PersonVO::class)

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
    fun createPerson(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.create(person)
    }

//    @PostMapping(value = ["/v2"], consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
//    fun createPersonV2(
//        @RequestBody person: PersonVOV2
//    ): PersonVOV2 {
//        return personService.createV2(person)
//    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @Operation(
        summary = "Updates a person information", description = "Updates a person information",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = PersonVO::class)

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
    fun updatePerson(
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.update(person)
    }

    @CrossOrigin(origins = ["http://localhost:8080"])
    @PatchMapping(value = ["/{id}"])
    @Operation(
        summary = "Disable a person", description = "Disable a person",
        tags = ["People"], responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = PersonVO::class)

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
    fun disablePersonById(
        @PathVariable(value = "id") id: Long,
    ): PersonVO {
        return personService.disabledPerson(id)
    }


    @DeleteMapping(value = ["/{id}"])
    @Operation(
        summary = "Deletes a person", description = "Deletes a person",
        tags = ["People"], responses = [

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
    fun deletePerson(
        @PathVariable(value = "id") id: Long,
    ): ResponseEntity<*> {
        personService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}

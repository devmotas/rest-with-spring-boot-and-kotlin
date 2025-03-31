package com.example.mapper.custom

import com.example.data.vo.v1.BookVO
import com.example.model.Book
import org.springframework.stereotype.Service

@Service
class BookMapper {

    fun mapEntityToVO(book: Book): BookVO {
        val vo = BookVO()
        vo.key = book.id
        vo.author = book.author
        vo.launchDate = book.launchDate
        vo.price = book.price
        vo.title = book.title
        return vo
    }

    fun mapVOToEntity(vo: BookVO): Book {
        val book = Book()
        book.id = vo.key
        book.author = vo.author
        book.launchDate = vo.launchDate
        book.price = vo.price
        book.title = vo.title
        return book
    }

}

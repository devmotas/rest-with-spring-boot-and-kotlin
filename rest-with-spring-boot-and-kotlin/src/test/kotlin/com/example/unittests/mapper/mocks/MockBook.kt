package com.example.unittests.mapper.mocks

import com.example.data.vo.v1.BookVO
import com.example.model.Book
import java.util.*


class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val persons: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            persons.add(mockEntity(i))
        }
        return persons
    }

    fun mockVOList(): ArrayList<BookVO> {
        val persons: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            persons.add(mockVO(i))
        }
        return persons
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.id = number.toLong()
        book.author = "Author Test$number"
        book.launchDate = Date()
        book.price = 25.0 + number
        book.title = "Title Test$number"
        return book
    }

    fun mockVO(number: Int): BookVO {
        val vo = BookVO()
        vo.key = number.toLong()
        vo.author = "Author Test$number"
        vo.launchDate = Date()
        vo.price = 25.0 + number
        vo.title = "Title Test$number"
        return vo
    }
}
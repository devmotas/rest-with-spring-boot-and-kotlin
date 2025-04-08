package br.com.erudio.integrationtests.vo.wrappers

import br.com.erudio.integrationtests.vo.BookVO
import br.com.erudio.integrationtests.vo.PersonVO
import com.fasterxml.jackson.annotation.JsonProperty

class BookEmbeddedVO {

    @JsonProperty("bookVOList")
    var books: List<BookVO>? = null
}
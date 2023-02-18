package com.example.basiccrud

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/author")
class AuthorController(
    private val authorService: AuthorService
) {

    @Autowired
    lateinit var authorRepo: AuthorRepo
    @Autowired
    lateinit var postController: PostController

    @GetMapping("/author-list")
    fun allAuthors(@RequestParam(name = "s", defaultValue = "name", required = false) sortBy : String): List<Author> =
        authorService.sortAllAuthors(sortBy)

    
    @GetMapping("/{authorId}")
    fun authorById(@PathVariable authorId: Long): Author = authorService.getAuthorById(authorId)

    data class AuthorInput(
        @field: NotBlank
        var name: String
    )
    
    @PostMapping("/author-list")
    fun createAuthor(@Valid @RequestBody authorInput: AuthorInput) = authorService.createAuthor(authorInput)

    
    @PutMapping("/{authorId}")
    fun updateAuthor(@PathVariable authorId: Long,@Valid @RequestBody authorInput: AuthorInput) = authorService.updateAuthor(authorId, authorInput)
    
    @DeleteMapping("/{authorId}")
    fun deleteAuthor(@PathVariable authorId: Long) = authorService.deleteAuthor(authorId)

}
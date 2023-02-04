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
class AuthorController {

    @Autowired
    lateinit var authorRepo: AuthorRepo
    @Autowired
    lateinit var postController: PostController

    @GetMapping("/authorList")
    fun allAuthors(@RequestParam(name = "s", defaultValue = "name", required = false) sortBy : String): List<Author>{
        with(arrayOf("name","authorId")){
            if (sortBy !in this){
                throw UnexpectedArgument(msg = "can not sort using $sortBy", *this)
            }
        }
        return authorRepo.findAll(Sort.by(sortBy))
    }
    
    @GetMapping("/{authorId}")
    fun authorById(@PathVariable authorId: Long): Author {
        return authorRepo.findByIdOrNull(authorId) ?: throw NotFound(msg = "Author not found with id: $authorId")
    }

    data class AuthorInput(
        @field: NotBlank
        var name: String
    )
    
    @PostMapping("/authorList")
    fun createAuthor(@Valid @RequestBody authorInput: AuthorInput){
        authorRepo.save(Author(authorInput.name))
    }
    
    @PutMapping("/{authorId}")
    fun updateAuthor(@PathVariable authorId: Long,@Valid @RequestBody authorInput: AuthorInput){
        val existOfAuthor = authorById(authorId)
        existOfAuthor.name = authorInput.name
        authorRepo.save(existOfAuthor)
    }
    
    @DeleteMapping("/{authorId}")
    fun deleteAuthor(@PathVariable authorId: Long) {
        val existOfAuthor = authorById(authorId)
        for (i in existOfAuthor.posts){
            postController.postRepo.delete(i)
        }
        authorRepo.delete(existOfAuthor)
    }
}
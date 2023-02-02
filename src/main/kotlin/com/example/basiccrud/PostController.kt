package com.example.basiccrud

import com.fasterxml.jackson.databind.BeanDescription
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.validation.BindingResult
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
@RequestMapping("/post")
class PostController {

    @Autowired
    lateinit var postRepo: PostRepo
    @Autowired
    lateinit var authorRepo: AuthorRepo

    @GetMapping("/list/{authorId}")
    fun allPostOfAuthor(@PathVariable authorId: Long): Set<Post>{
        return authorRepo.findByIdOrNull(authorId) ?.posts ?: setOf()
    }

    @GetMapping("/list")
    fun allPosts(@RequestParam(name="s", required = false , defaultValue = "title") sortBy: String): List<Post> {

        with(arrayOf("title","postId", "description")){
            if(sortBy !in this){
                throw UnexpectedArgument("can't sort using $sortBy", *this)
            }
        }

        return postRepo.findAll(Sort.by(sortBy))
    }

    @GetMapping("/{id}")
    fun postById(@PathVariable id: Long): Post {
        return postRepo.findByIdOrNull(id) ?: throw NotFound("post not found with id: $id")
    }

    data class PostInput(
        @field: NotBlank(message = "You must input Your Title.")
        val title: String,
        @field: NotBlank(message = "You must input your description.")
        val description: String)


    @PostMapping("/{authorId}")
    fun createPost(@PathVariable authorId : Long, @Valid @RequestBody postInput: PostInput) {
        val author = authorRepo.findByIdOrNull(authorId) ?: throw NotFound("author not found with id: $authorId.")
        postRepo.save(Post(author,postInput.title, postInput.description))
    }

    @PutMapping("/{id}")
    fun updateTitleAndDescriptionById(@PathVariable id: Long, @Valid @RequestBody postInput: PostInput){

        //val existingTitle = postRepo.findByIdOrNull(id) ?: throw NotFound("post does not exit with this id: $id")
        val existingTitleAndDescription = postById(id)
        existingTitleAndDescription.title = postInput.title
        existingTitleAndDescription.description = postInput.description
        postRepo.save(existingTitleAndDescription)
    }

    @DeleteMapping("/{id}")
    fun deleteTitleAndDescriptionById(@PathVariable id: Long) {
        val existingTitleAndDescription = postById(id)
        postRepo.delete(existingTitleAndDescription)
    }

}
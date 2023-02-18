package com.example.basiccrud

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
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
class PostController(
    private val postService: PostService
) {

    @Autowired
    lateinit var postRepo: PostRepo
    @Autowired
    lateinit var authorRepo: AuthorRepo

    @GetMapping("/list/{authorId}")
    fun getAllPostsOfAuthor(@PathVariable authorId: Long): Set<Post> = postService.getAllPostsOfAuthor(authorId)


    @GetMapping("/list")
    fun getAllPosts(@RequestParam(name="s", required = false , defaultValue = "title") sortBy: String): List<Post> =
        postService.getAllPostsSortedBy(sortBy)


    @GetMapping("/{id}")
    fun postById(@PathVariable id: Long): Post = postService.getPostById(id)

    data class PostInput(
        @field: NotBlank(message = "You must input Your Title.")
        val title: String,
        @field: NotBlank(message = "You must input your description.")
        val description: String)


    @PostMapping("/{authorId}")
    fun createPost(@PathVariable authorId : Long, @Valid @RequestBody postInput: PostInput) =
        postService.createPost(authorId, postInput)


    @PutMapping("/{id}")
    fun updatePostById(@PathVariable id: Long, @Valid @RequestBody postInput: PostInput) =
        postService.updatePostById(id, postInput)


    @DeleteMapping("/{id}")
    fun deleteTitleAndDescriptionById(@PathVariable id: Long) =
        postService.deletePostById(id)

}
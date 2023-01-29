package com.example.basiccrud

import com.fasterxml.jackson.databind.BeanDescription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
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

    data class PostInput(val title: String, val description: String)

    @PostMapping("/")
    fun createPost(@RequestBody postInput: PostInput): Post =
        postRepo.save(Post(postInput.title, postInput.description))

    @PutMapping("/{id}")
    fun updateTitleAndDescriptionById(@PathVariable id: Long, @RequestBody post: Post){

        //val existingTitle = postRepo.findByIdOrNull(id) ?: throw NotFound("post does not exit with this id: $id")
        val existingTitle = postById(id)
        existingTitle.title = post.title
        existingTitle.description = post.description
        postRepo.save(existingTitle)
    }

}
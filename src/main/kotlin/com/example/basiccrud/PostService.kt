package com.example.basiccrud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService {

    @Autowired
    lateinit var postRepo: PostRepo
    @Autowired
    lateinit var authorRepo: AuthorRepo

    fun getAllPostsOfAuthor(authorId: Long): Set<Post> = authorRepo.findByIdOrNull(authorId)?.posts ?: setOf()

    fun getAllPostsSortedBy(sortBy: String): List<Post> {
        with(arrayOf("title","postId", "description")){
            if(sortBy !in this){
                throw UnexpectedArgument("can't sort using $sortBy", *this)
            }
        }
        return postRepo.findAll(Sort.by(sortBy))
    }

    fun getPostById(id: Long): Post = postRepo.findByIdOrNull(id) ?: throw NotFound("post not found with id: $id")

    fun createPost(authorId: Long, postInput: PostController.PostInput) {
        val author = authorRepo.findByIdOrNull(authorId) ?: throw NotFound("author not found with id: $authorId.")
        postRepo.save(Post(author, postInput.title, postInput.description))
    }

    fun updatePostById(id: Long, postInput: PostController.PostInput) {
        val existingPost = getPostById(id)
        existingPost.title = postInput.title
        existingPost.description = postInput.description
        postRepo.save(existingPost)
    }

    @Transactional
    fun deletePostById(id: Long) {
        val existingPost = getPostById(id)
        postRepo.delete(existingPost)
    }



}
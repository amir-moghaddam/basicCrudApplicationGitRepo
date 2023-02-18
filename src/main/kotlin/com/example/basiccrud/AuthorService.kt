package com.example.basiccrud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService {

    @Autowired
    lateinit var authorRepo: AuthorRepo
    @Autowired
    lateinit var postController: PostController

    fun sortAllAuthors(sortBy: String): List<Author>{
        with(arrayOf("name","authorId")){
            if (sortBy !in this){
                throw UnexpectedArgument(msg = "can not sort using $sortBy", *this)
            }
        }
        return authorRepo.findAll(Sort.by(sortBy))
    }

    fun getAuthorById(authorId: Long): Author {
        return authorRepo.findByIdOrNull(authorId) ?: throw NotFound(msg = "Author not found with id: $authorId")
    }

    fun createAuthor(authorInput: AuthorController.AuthorInput) = authorRepo.save(Author(authorInput.name))

    fun updateAuthor(authorId: Long, authorInput: AuthorController.AuthorInput) {
        val existOfAuthor = getAuthorById(authorId)
        existOfAuthor.name = authorInput.name
        authorRepo.save(existOfAuthor)
    }
    @Transactional
    fun deleteAuthor(authorId: Long){
        val existOfAuthor = getAuthorById(authorId)
        for (i in existOfAuthor.posts){
            postController.postRepo.delete(i)
        }
        authorRepo.delete(existOfAuthor)
    }
}
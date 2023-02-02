package com.example.basiccrud

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StartUp {
    @Autowired
    lateinit var postRepo: PostRepo
    @Autowired
    lateinit var authorRepo: AuthorRepo

    val logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun start() {

        val author1 = authorRepo.save(Author("Amir"))
        val author2 = authorRepo.save(Author("Judge"))

        logger.info("Start up started")

        for (i in 0 until 20) {
            val post1 = postRepo.save(Post(author1,"amir-$i", "spring-$i"))
            val post2 = postRepo.save(Post(author2, "Judge-$i", "spring-$i"))

            logger.info("post created $post1 and $post2")
        }

    }
}

package com.example.basiccrud

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StartUp {
    @Autowired
    lateinit var postRepo: PostRepo

    val logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun start() {
        logger.info("Start up started")
        for (i in 0 until 20) {
            val post = postRepo.save(Post("amir-$i", "spring-$i"))
            logger.info("post created $post")
        }

    }
}

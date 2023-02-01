package com.example.basiccrud

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.Instant


@Entity
class Post(
    @field: NotBlank
    var title: String,
    @field: NotBlank
    var description: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id", nullable = false)
    var postId: Long? = null

    @Column(name = "created_date", nullable = false)
    var createdDate: Long = Instant.now().epochSecond

    override fun toString(): String {
        return "Post(title='$title', description='$description', postId=$postId, createdDate=$createdDate)"
    }


}
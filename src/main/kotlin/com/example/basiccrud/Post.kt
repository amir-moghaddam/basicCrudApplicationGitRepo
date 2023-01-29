package com.example.basiccrud

import jakarta.persistence.*

@Entity
class Post(
    var title: String,
    var description: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id", nullable = false)
    var postId: Long? = null

    override fun toString(): String {
        return "Post(title='$title', description='$description', postId=$postId)"
    }


}
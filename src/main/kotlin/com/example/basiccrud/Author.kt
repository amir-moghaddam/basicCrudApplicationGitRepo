package com.example.basiccrud

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Author(
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "author_id", nullable = false)
    var authorId: Long? = null

    @JsonManagedReference
    @OneToMany(mappedBy = "author")
    var posts : Set<Post> = setOf()
}
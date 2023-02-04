package com.example.basiccrud

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
class Author(
    @field: NotBlank
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "author_id", nullable = false)
    var authorId: Long? = null

    @JsonManagedReference
    @OneToMany(mappedBy = "author")
    var posts : Set<Post> = setOf()
    override fun toString(): String {
        return "Author(name='$name', authorId=$authorId, posts=$posts)"
    }


}
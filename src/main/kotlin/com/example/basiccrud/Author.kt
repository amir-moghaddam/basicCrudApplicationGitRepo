package com.example.basiccrud

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date

@Entity
@EntityListeners(AuditingEntityListener :: class)
class Author(
    @field: NotBlank
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "author_id", nullable = false)
    var authorId: Long? = null

    @CreatedDate
    @Column(name = "created_date")
    lateinit var createdDate: Date
    @LastModifiedDate
    lateinit var modifiedDate: Date

    @JsonManagedReference
    @OneToMany(mappedBy = "author")
    var posts : Set<Post> = setOf()

    override fun toString(): String {
        return "Author(name='$name', authorId=$authorId, posts=$posts)"
    }


}
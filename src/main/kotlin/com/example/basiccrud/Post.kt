package com.example.basiccrud

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.annotation.PostConstruct
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date


@Entity
@EntityListeners(AuditingEntityListener :: class)
class Post(
    @JsonBackReference
    @ManyToOne
    val author: Author,

    @field: NotBlank
    var title: String,

    @field: NotBlank
    var description: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id", nullable = false)
    var postId: Long? = null

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    lateinit var createdDate: Date
    @LastModifiedDate
    lateinit var modifiedDate : Date

    override fun toString(): String {
        return "Post(title='$title', description='$description', postId=$postId, createdDate=$createdDate)"
    }


}
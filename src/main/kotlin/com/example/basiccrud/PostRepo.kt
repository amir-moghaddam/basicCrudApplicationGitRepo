package com.example.basiccrud

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepo: JpaRepository < Post , Long > {
}
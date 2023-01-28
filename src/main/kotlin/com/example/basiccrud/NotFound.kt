package com.example.basiccrud

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFound(msg:String): IllegalArgumentException(msg) {
}